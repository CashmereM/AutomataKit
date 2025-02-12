package e222531G.automata.kit.core

/**
 * Abstract class representing an acceptor in the context of an automaton.
 * An acceptor determines whether a given input string (expression) is accepted by the automaton,
 * which is defined by a set of states, transitions, an initial state, and a set of accepting (final) states.
 *
 * <p>The {@link #accepts(String, State, Map, List, int)} method must be implemented by subclasses to define
 * the automaton's logic for accepting or rejecting an input string based on the state machine's structure.</p>
 *
 * @see State
 * @see Automaton
 */
abstract class AbstractAcceptor {

    /**
     * Evaluates whether the given expression is accepted by the automaton.
     * This method should implement the logic for simulating the automaton's operation:
     * it will transition between states based on the input expression and will check if it reaches one of the
     * final states within the defined transition rules.
     *
     * <p>The automaton typically starts in the initial state and processes the input expression one character at a time.
     * If, after processing the entire expression, the automaton is in one of the accepting (final) states
     * and it travels all the expression's character, it returns {@code true}, indicating acceptance of the input.
     * Otherwise, it returns {@code false}.</p>
     * @param expression The input string to be processed by the automaton.
     * @param initialState The starting state of the automaton.
     * @param states A map of all states in the automaton, where each state has a unique identifier.
     * @param finalStates A list of states that are considered accepting (final) states in the automaton.
     * @param maxCharLength The maximum number of characters that can be processed in the input string.
     *
     * @return {@code true} if the expression is accepted by the automaton, {@code false} otherwise.
     */
    abstract fun accepts(
        expression : String,
        initialState : State,
        states : Map<String, State>,
        finalStates : List<State>,
        maxCharLength : Int
    ) : Boolean

}