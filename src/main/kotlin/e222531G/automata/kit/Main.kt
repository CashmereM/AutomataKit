package e222531G.automata.kit

import e222531G.automata.kit.models.AutomataData
import e222531G.automata.kit.core.Automata
import kotlinx.serialization.json.*

fun main() {
    val filePath = "automatas/smiley.json"
    val fileContent = object {}.javaClass.classLoader.getResource(filePath)?.readText() ?: return
    val automate = Json.decodeFromString<AutomataData>(fileContent)
    val test = Automata.createAutomata(automate)
    println(test.accepts(":-7)"))
}