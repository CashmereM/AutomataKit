package e222531G.automata.kit.models

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

    fun findNextStateName(symbol: String): String? {
        val transitionFinded = transitions.find { it.symbol == symbol }
        return transitionFinded?.to?.name
    }
}