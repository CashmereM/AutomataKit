package e222531G.automata.kit.core

class AcceptorA : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>,
        maxCharLength : Int
    ): Boolean {
        var currentState: State = initialState
        var index = 0

        while (index < expression.length) {
            val char = expression[index].toString()
            val nextState = currentState.findState(char)

            if (nextState == null) {
                return false
            } else {
                // Ensure the next state is valid in the states map
                if (nextState !== states[nextState.name]) {
                    throw AutomatonException("State transition mismatch for '$char'.")
                }
                currentState = nextState
            }
            index++
        }

        return finalStates.contains(currentState) && index == expression.length
    }
}