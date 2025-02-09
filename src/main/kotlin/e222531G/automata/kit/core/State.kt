package e222531G.automata.kit.core

class State(
    val name: String,
    val transitions: MutableList<Transition> = mutableListOf()
) {

    fun addTransition( transtion : Transition ) {
        transitions.add(transtion)
    }

    fun findNextState(symbol: String): State? {
        return transitions.find { it.symbol == symbol }?.to
    }
}