package e222531G.automata.kit.core

import e222531G.automata.kit.exceptions.AutomatonException

class AcceptorC : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>,
        maxCharLength : Int
    ): Boolean {
        var remainingExpression = expression
        var currentState = initialState

        while (currentState !in finalStates && remainingExpression.isNotBlank()) {
            var nextState: State? = null
            var transitionPrefix = ""

            // Try to match the longest possible prefix based on maxCharLength
            for (i in 0 until maxCharLength) {
                if (i >= remainingExpression.length) break
                transitionPrefix += remainingExpression[i]

                // If a valid transition is found, break early
                if (currentState.transitions.containsKey(transitionPrefix)) {
                    nextState = currentState.findState(transitionPrefix)
                    break
                }
            }

            // If no valid transition found, return false
            if (nextState == null) {
                return false
            }

            // Update the remaining expression and current state
            remainingExpression = remainingExpression.removePrefix(transitionPrefix)
            // Ensure the next state is valid in the states map
            if (nextState !== states[nextState.name]) {
                throw AutomatonException("State transition mismatch for '$transitionPrefix'.")
            }
            currentState = nextState
        }

        // Ensure we're in a final state and the expression is fully consumed
        return currentState in finalStates && remainingExpression.isBlank()
    }
}