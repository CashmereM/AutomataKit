package e222531G.automata.kit.core

import e222531G.automata.kit.exceptions.AutomatonException

class State(
    val name: String,
    val transitions: MutableMap<String, State> = mutableMapOf()
) {
    init {
        if (name == "") {
            throw AutomatonException("The state can't have an empty name")
        }
    }

    fun addTransition( character : String, to : State ) {
        transitions[character] = to
    }

    fun findState(character: String): State? {
        return transitions[character]
    }
}