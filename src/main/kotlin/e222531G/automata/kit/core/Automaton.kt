package e222531G.automata.kit.core

import e222531G.automata.kit.exceptions.AutomatonException
import e222531G.automata.kit.models.AutomatonData


/**
 * Represents an Automaton, a finite state machine that processes input based on its states, alphabet, and transitions.
 * This class is responsible for managing the states, transitions, and determining whether an input expression is accepted
 * by the automaton. The automaton may have an initial state, a set of final (accepting) states, and an alphabet defining
 * the possible symbols for transitions between states.
 *
 * <p>An automaton is created using the {@link createAutomata} method with an instance of {@link AutomataData}.
 * The automaton can then evaluate whether a given expression is accepted by calling {@link accepts}.</p>
 *
 * @param name The name of the automaton.
 * @param initialState The initial state of the automaton.
 * @param _alphabet A list of symbols representing the alphabet used by the automaton.
 *
 * @see State
 * @see AbstractAcceptor
 * @see AutomatonException
 * @see AcceptorA
 * @see AcceptorC
 */
class Automaton private constructor(
    val name: String,
    private val initialState : State,
    private val _alphabet: Set<String> = mutableSetOf()
) {
    var description: String = ""
    val alphabet : List<String>
        get() = _alphabet.toList()
    private val states: MutableMap<String, State> = mutableMapOf()
    private val finalStates: MutableList<State> = mutableListOf()
    private var acceptor : AbstractAcceptor

    /**
     * The maximum length of symbols in the automaton's alphabet.
     */
    private val maxCharLength : Int

    init {
        states[initialState.name] = initialState
        if ( _alphabet.isEmpty() ){
            throw AutomatonException("This automata can't have an empty alphabet : { name : ${this.name}")
        }
        maxCharLength = this._alphabet.maxOf { it.length }
        acceptor = if ( _alphabet.find { it.length > 1 } != null ){
            AcceptorC()
        } else {
            AcceptorA()
        }
    }


    /**
     * Adds a new state to the automaton.
     *
     * @param name The name of the state to add.
     * @throws AutomatonException If a state with the same name already exists.
     */
    private fun addState( name : String ) {
        if ( this.states.containsKey(name) ){
            throw AutomatonException("This automata already had a state named : $name")
        } else {
            this.states[name] = State(name)
        }
    }

    /**
     * Adds a transition between two states in the automaton.
     *
     * @param currentStateName The name of the current state.
     * @param symbol The symbol that triggers the transition.
     * @param transitionStateName The name of the state to transition to.
     * @throws AutomatonException If any of the states or the symbol are not valid.
     */
    private fun addTransition( currentStateName : String, symbol : String, transitionStateName : String ) {
        val currentState = this.states[currentStateName]
        val transitionState = this.states[transitionStateName]
        if ( currentState == null ){
            throw AutomatonException("This automata doesn't have a state named (currentState) : $currentStateName")
        } else if ( transitionState == null) {
            throw AutomatonException("This automata doesn't have a state named (transitionState): $transitionStateName")
        } else if ( !this._alphabet.contains(symbol)) {
            throw AutomatonException("This automata doesn't have the symbol in their alphabet: $symbol")
        } else {
            currentState.addTransition(symbol, transitionState)
        }
    }


    /**
     * Adds a final (accepting) state to the automaton.
     *
     * @param name The name of the state to be added as a final state.
     * @throws AutomatonException If the state with the given name doesn't exist in the automaton.
     */
    private fun addFinalState( name : String ) {
        val finalState = this.states[name]
            ?: throw AutomatonException("This automata doesn't have a state named (automata final state) : $name")
        this.finalStates.add(finalState)
    }


    /**
     * Determines if the given expression is accepted by the automaton.
     *
     * @param expression The input string to be evaluated by the automaton.
     * @return {@code true} if the expression is accepted, {@code false} otherwise.
     */
    fun accepts(expression: String): Boolean {
        val result = acceptor.accepts(
            expression,
            initialState,
            states,
            finalStates,
            maxCharLength
        )
        return result
    }

    companion object{

        private val AUTOMATONS : MutableMap<String, Automaton> = mutableMapOf()
        private const val LIMIT = 100

        fun getAutomatons() : Map<String, Automaton>{
            return AUTOMATONS.toMap()
        }


        /**
         * Creates a new automaton based on the provided automaton data.
         *
         * @param base The data that defines the automaton (name, initial state, alphabet, etc.).
         * @return The newly created automaton.
         * @throws AutomatonException If there is an error creating the automaton (e.g., invalid states or transitions).
         * @throws AutomatonException If the maximum of automata created is reached.
         */
        fun createAutomata( base : AutomatonData ) : Automaton{
            if (AUTOMATONS.size == LIMIT) {
                throw AutomatonException("Maximum number of automata reached.")
            }
            val initialState = State(base.initialState)
            val newAutomaton = Automaton(base.name, initialState, base.alphabet)
            newAutomaton.description = base.description
            createStates(newAutomaton, base)
            createTransition(newAutomaton, base)
            AUTOMATONS[newAutomaton.name] = newAutomaton
            return newAutomaton
        }


        /**
         * Creates the transitions for the automaton based on the provided automaton data.
         *
         * @param automaton The automaton to add the transitions to.
         * @param base The data that defines the transitions.
         * @throws AutomatonException If there is an error creating the transitions.
         */
        private fun createStates(automaton : Automaton, base : AutomatonData){
            try{
                base.states.filter { it.name != base.initialState }.forEach{
                    automaton.addState(it.name)
                }
                base.finalStates.forEach { name ->
                    automaton.addFinalState(name)
                }
            }catch ( e : AutomatonException) {
                throw AutomatonException("""
                    Phase - creating states - name : ${automaton.name}
                        ${e.message}
                    => states list [${base.states.joinToString("; ") { it.name }}]
                """.trimIndent())

            }
        }

        /**
         * Creates the transitions for the automaton based on the provided automaton data.
         *
         * @param automaton The automaton to add the transitions to.
         * @param base The data that defines the transitions.
         * @throws AutomatonException If there is an error creating the transitions.
         */
        private fun createTransition(automaton: Automaton, base: AutomatonData){
            if ( automaton.states.isEmpty() ){
                throw AutomatonException("""
                    Phase - creating transitions - name : ${automaton.name}
                        states list empty
                """.trimIndent())
            }
            try{
                base.states.forEach { stateData ->
                    stateData.transitions.forEach {
                        automaton.addTransition(stateData.name, it.symbol, it.to)
                    }
                }
            }catch (e : AutomatonException) {
                val message = e.message
                throw AutomatonException(
                    """
                        Phase - creating transitions - name : ${automaton.name} 
                            $message
                    """.trimIndent()
                )
            }
        }
    }

}