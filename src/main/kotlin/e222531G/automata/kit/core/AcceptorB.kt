package e222531G.automata.kit.core

class AcceptorB : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>
    ): Boolean {
        var currentState = initialState
        while ( currentState !in finalStates ){
            val transition = currentState.transitions.find { expression.startsWith(it.symbol) }
            if ( transition == null ){
                return false
            }
            expression.removePrefix(transition.symbol)
            currentState = transition.to
        }
        return false
    }
}