package e222531G.automata.kit.core

class State(
    val name: String,
    val transitions: MutableMap<String, State> = mutableMapOf()
) {

    fun addTransition( character : String, to : State ) {
        transitions[character] = to
    }

    fun findState(character: String): State? {
        return transitions[character]
    }
}