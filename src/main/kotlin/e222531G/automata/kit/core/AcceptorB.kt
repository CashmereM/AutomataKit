package e222531G.automata.kit.core

class AcceptorB : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>
    ): Boolean {
        var expr : String = expression
        var currentState = initialState
        val listTransitions = mutableListOf<String>()
        while ( currentState !in finalStates && expr != "" ){
            val transition = currentState.transitions.find { transition -> expr.startsWith(transition.symbol) }
            if ( transition == null ){
                return false
            }
            listTransitions.add(transition.symbol)
            expr = expr.removePrefix(transition.symbol)
            currentState = transition.to
        }
        return true
    }
}

