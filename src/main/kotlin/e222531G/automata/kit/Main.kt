package e222531G.automata.kit

import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.utils.AutomatonReader
import e222531G.automata.kit.utils.DotExporter

fun main() {
    val filePath = "automatas/json/function.json"
    val automatonReader = AutomatonReader()
    val automatonData = automatonReader.readAutomatonJsonFile(filePath)
    val test = Automaton.createAutomata(automatonData)
    println("result : ${test.accepts(":-)")}")
    DotExporter().export(automatonData)
}