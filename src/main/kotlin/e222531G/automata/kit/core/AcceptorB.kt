package e222531G.automata.kit.core

class AcceptorB : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>,
        maxCharLength : Int
    ): Boolean {
        var remainingExpression: String = expression
        var currentState = initialState

        // Loop until the current state is an accepting state or the expression is consumed
        while (currentState !in finalStates && remainingExpression.isNotEmpty()) {
            // Find the first transition whose key matches the start of the remaining expression
            val transition = currentState.transitions.entries.find { (prefix, _) -> remainingExpression.startsWith(prefix) }

            if (transition == null) {
                return false
            }

            // Remove the matching prefix from the remaining expression
            remainingExpression = remainingExpression.removePrefix(transition.key)


            // Move to the next state according to the transition
            currentState = transition.value

            // Ensure the next state is valid in the states map
            if (currentState !== states[currentState.name]) {
                throw AutomatonException("State transition mismatch for '${transition.key}'.")
            }
        }

        // Ensure the automaton ends in an accepting state and the expression is fully consumed
        return currentState in finalStates && remainingExpression.isEmpty()
    }
}

