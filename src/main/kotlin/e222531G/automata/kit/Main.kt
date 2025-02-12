package e222531G.automata.kit

import e222531G.automata.kit.models.AutomataData
import e222531G.automata.kit.core.Automaton
import kotlinx.serialization.json.*

fun main() {
    val filePath = "automatas/function.json"
    val fileContent = object {}.javaClass.classLoader.getResource(filePath)?.readText() ?: return
    val automate = Json.decodeFromString<AutomataData>(fileContent)
    val test = Automaton.createAutomata(automate)
    println(test.accepts("function test(x: String, y: Char): String"))
}