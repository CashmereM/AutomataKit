package e222531G.automata.kit.core

class AcceptorA : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>
    ): Boolean {
        var currentState : State = initialState
        var index = 0
        while (index < expression.length){
            val char = expression[index].toString()
            val nextState = currentState.findNextState(char)
            if ( nextState == null){
                return false
            }else{
                if ( nextState !== states[nextState.name]){
                    throw AutomataExeption("""
                        
                    """.trimIndent())
                }
                currentState = nextState
            }
            index++
        }
        return finalStates.contains(currentState) && index == expression.length
    }
}