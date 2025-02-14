package e222531G.automata.kit.scripts

import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.utils.AutomatonReader

class InstantiateAutomatons() {
    private val files : MutableList<String> = mutableListOf()
    private val reader = AutomatonReader()

    init {
        files.add("automatas/aut/smiley.aut")
        files.add("automatas/json/function.json")
        files.add("automatas/aut/hour.aut")
        files.add("automatas/aut/email.aut")
    }

    fun instantiate() {
        files.forEach { file ->
            val automatonData = reader.readAutomatonFile(file)
            Automaton.createAutomata(automatonData)
        }
    }
}