package e222531G.automata.kit.utils

import e222531G.automata.kit.exceptions.AutomatonException
import e222531G.automata.kit.exceptions.*
import e222531G.automata.kit.models.AutomatonData
import e222531G.automata.kit.models.StateData
import e222531G.automata.kit.models.TransitionData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

class AutomatonReader {

    private var lastFilePath = ""

    fun readAutomatonJsonFile( filePath : String ) : AutomatonData {
        this.lastFilePath = filePath
        if ( !filePath.endsWith(".json")){
            throw IllegalArgumentException("The file should be a '.json' file , have : $filePath")
        }
        val fileContent = object {}.javaClass.classLoader.getResource(filePath)?.readText() ?: throw FileNotFoundException(" The file '${filePath}'")
        try{
            val automatonData = Json.decodeFromString<AutomatonData>(fileContent)
            return automatonData
        }catch(e : SerializationException){
            throw AutomatonException("Error parsing json \n     $e") // à changer
        }
    }

    fun readAutomatonAuthFile( filePath : String ) : AutomatonData {
        this.lastFilePath = filePath
        if ( !filePath.endsWith(".auth")){
            throw IllegalArgumentException("The file should have '.auth' extension have : $lastFilePath")
        }
        val inputStream = object {}.javaClass.classLoader.getResourceAsStream(filePath) ?:
            throw FileNotFoundException(" The file '${lastFilePath}' doesn't exists the resources directory")
        val lines = inputStream.bufferedReader().useLines { it.toMutableList() }
        return parseAutomaton(lines)
    }

    private fun getFieldValue(lines: MutableList<String>, fieldName: String, separatingWords: Boolean = false): List<String> {
        val regex = Regex("^$fieldName:\\s*(.*)")
        val line = lines.find { line ->
            regex.containsMatchIn(line)
        } ?: throw MissingRequiredFieldException("Missing field $fieldName : $lastFilePath")

        val value = regex.find(line)?.groupValues?.get(1) ?: ""

        if (fieldName == "description") {
            return listOf(value)
        }
        val splitValues = if (separatingWords) value.split(" ").filter { it.isNotEmpty() } else listOf(value)

        if (splitValues.isEmpty()) {
            throw MissingFieldValueException("The $fieldName doesn't have a value : $lastFilePath")
        }
        return splitValues
    }


    private fun parseAutomaton( lines: MutableList<String> ) : AutomatonData{
        val name = getFieldValue(lines, "name")[0]
        val description = getFieldValue(lines, "description")[0]
        val initialState = getFieldValue(lines, "initial state", separatingWords = true)[0]
        val finalStates = getFieldValue(lines, "final states", separatingWords = true)
        val states : MutableMap<String, StateData> = mutableMapOf()
        val alphabet : MutableSet<String> = mutableSetOf()
        if ( name.isEmpty() ){
            throw MissingFieldValueException("The name field have no value : $lastFilePath")
        }
        if (initialState.isEmpty()){
            throw MissingFieldValueException("The initial state field have no value : $lastFilePath")
        }
        if (finalStates.isEmpty()){
            throw MissingFieldValueException("The final states field have no value : $lastFilePath")
        }

        val transitions = extractTransitions(lines)
        if (transitions.isEmpty()){
            throw InvalidFileFormatException("No transitions specified : $lastFilePath")
        }
        transitions.forEach { transition ->
            val state = states.getOrPut(transition[0]) {
                StateData(transition[0], mutableListOf())
            }
            val character = transition[1]
            val to = transition[2]
            states.getOrPut(to,){
                StateData(to, mutableListOf())
            }
            state.transitions.add(TransitionData(character, to))
            alphabet.add(character)
        }
        return AutomatonData(name = name, description = description, initialState = initialState, finalStates = finalStates, states = states.values.toList(), alphabet = alphabet)
    }

    private fun extractTransitions(lines: MutableList<String>): List<List<String>> {
        val transitionRegex = Regex("^- \\w+\\s+\\S+\\s+\\w+$")
        val transitions = lines.filter { line ->
            transitionRegex.matches(line)
        }.map { it.removePrefix("- ") }.map { transition ->
            val result = transition.split(" ")
            if ( result.size != 3 ){
                throw InvalidFieldFormat("")
            }
            result
        }
        return transitions
    }





    /*
    private fun parseName( line : String ) : String {
        val elements = line.split(":").filter { it != "" }
        if ( elements.size != 2 ){
            throw AutomatonException("The file doesn't provide a name (required) for the automaton : $lastFilePath")
        }
        return elements[1]
    }

    private fun parseInitialState(line: String): String {
        val elements = splitLine(line)
        if (elements.size != 2) {
            throw AutomatonException("The file doesn't provide an initial state (required) for the automaton.")
        }
        val list = splitLine(elements[1], " ")
        if ( list.isEmpty() ){
            throw MissingRequiredFieldException("The field 'initial state' doesn't have a value")
        } else if ( list.size > 1 ){
            throw InvalidFileFormatException("The field 'initial state' have too much value")
        }
        val initialState = list.first()
        return initialState
    }

    private fun parseDescription(line: String) : String {
        val elements = line.split(":").filter { it != "" }.onEach {
            val result = it.removePrefix(" ")
            return result
        }
        var description = ""
        if ( elements.size == 2 ){
            description = elements[1]
        }
        return description
    }

    private fun parseFinalStates(line: String, statesList: MutableMap<String, StateData>, finalStates : MutableList<String>) {
        val elements = splitLine(line)
        if ( elements.size != 2 ){
            throw AutomatonException("The file doesn't provide finals states (required) for the automaton : $lastFilePath") // Change required
        }
        finalStates.addAll(elements[1].split(" ").filter { it != "" }.toMutableList())
        if ( finalStates.isEmpty() ){
            throw AutomatonException("The file doesn't provide finals states (required) for the automaton : $lastFilePath")
        }
        finalStates.forEach { name ->
            if ( statesList[name] == null){
                statesList[name] = StateData(name, mutableListOf())
            }
        }
    }

    private fun parseTransition(line: String, statesList: MutableMap<String, StateData>, alphabet: MutableSet<String>) {
        val elements = splitLine(line).map {
            it.replace(" ", "")
        }.filter { it != "" }
        if (elements.size != 3) {
            throw InvalidFieldFormat("Invalid transition format : " +
                    "\n file path : $lastFilePath" +
                    "\n line : $line" +
                    "\n     $elements"
            )
        }
        val (currentStateName, character, nextStateName) = elements
        val currentState = statesList.getOrPut(currentStateName) {
            StateData(currentStateName, mutableListOf())
        }
        currentState.transitions.add(TransitionData(character, nextStateName))
        alphabet.add(character)
    } */
}