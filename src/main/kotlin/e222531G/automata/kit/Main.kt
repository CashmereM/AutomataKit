package e222531G.automata.kit

import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.utils.AutomatonReader

fun main() {
    val filePath = "automatas/auth/smiley.auth"
    val automatonReader = AutomatonReader()
    val automatonData = automatonReader.readAutomatonAuthFile(filePath)
    val test = Automaton.createAutomata(automatonData)
    println("result : ${test.accepts(":-)")}")
}