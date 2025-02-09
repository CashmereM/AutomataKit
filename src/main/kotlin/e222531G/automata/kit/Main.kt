package e222531G.automata.kit

import e222531G.automata.kit.models.AutomataData
import e222531G.automata.kit.core.Automata
import kotlinx.serialization.json.*

fun main() {
    val json = """
        {
          "name": "Smiley",
          "description": "Seuls les expressions suivantes sont valides : :), :-), :(, ;-), :=), ]-)",
          "alphabet": [":", ";", "-", "=", "(", ")", "]"],
          "states": [
            {"name": "e0", "transitions": [{"symbol": ";", "to": "e2"}, {"symbol": "]", "to": "e2"}, {"symbol": ":", "to": "e1"}]},
            {"name": "e1", "transitions": [{"symbol": "-", "to": "e3"}, {"symbol": "=", "to": "e3"}, {"symbol": ")", "to": "e4"}, {"symbol": "(", "to": "e4"}]},
            {"name": "e2", "transitions": [{"symbol": "-", "to": "e3"}]},
            {"name": "e3", "transitions": [{"symbol": ")", "to": "e4"}]},
            {"name": "e4", "transitions": []}
          ],
          "initialState": "e0",
          "finalStates": ["e4"]
        }
    """

    val filePath = "automatas/function.json"
    val fileContent = object {}.javaClass.classLoader.getResource(filePath)?.readText() ?: return
    val automate = Json.decodeFromString<AutomataData>(fileContent)
    val test = Automata.createAutomata(automate)
    println(test.accepts("function print(x: Int, y: Int): String"))
}