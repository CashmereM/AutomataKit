package e222531G.automata.kit.core

import e222531G.automata.kit.models.AutomataData

class Automata private constructor(
    val name: String,
    private val initialState : State,
    private val _alphabet: List<String> = mutableListOf()
) {
    var description: String = ""
    val alphabet : List<String>
        get() = _alphabet.toList()
    private val states: MutableMap<String, State> = mutableMapOf()
    private val finalStates: MutableList<State> = mutableListOf()
    private var acceptor : AbstractAcceptor

    init {
        states[initialState.name] = initialState
        acceptor = if ( _alphabet.find { it.length > 1 } != null ){
            AcceptorB()
        } else {
            AcceptorA()
        }
    }

    private fun addState( name : String ) {
        if ( this.states.containsKey(name) ){
            throw AutomataExeption("This automata already had a state named : $name")
        } else {
            this.states[name] = State(name)
        }
    }

    private  fun addTransition( currentStateName : String, symbol : String, transitionStateName : String ) {
        val currentState = this.states[currentStateName]
        val transitionState = this.states[transitionStateName]
        if ( currentState == null ){
            throw AutomataExeption("This automata doesn't have a state named (currentState) : $currentStateName")
        } else if ( transitionState == null) {
            throw AutomataExeption("This automata doesn't have a state named (transitionState): $transitionStateName")
        } else if ( !this._alphabet.contains(symbol)) {
            throw AutomataExeption("This automata doesn't have the symbol in their alphabet: $symbol")
        } else {
            val newTransition = Transition(symbol, transitionState)
            currentState.addTransition(newTransition)
        }
    }


    private fun addFinalState( name : String ) {
        val finalState = this.states[name]
            ?: throw AutomataExeption("This automata doesn't have a state named (automata final state) : $name")
        this.finalStates.add(finalState)
    }

    fun accepts(expression: String): Boolean {
        val result = acceptor.accepts(
            expression,
            initialState,
            states,
            finalStates
        )
        return result
    }

    companion object{
        fun createAutomata( base : AutomataData ) : Automata{
            val initialState = State(base.initialState)
            val newAutomata = Automata(base.name, initialState, base.alphabet)
            newAutomata.description = base.description
            createStates(newAutomata, base)
            createTransition(newAutomata, base)
            return newAutomata
        }

        private fun createStates(automata : Automata, base : AutomataData){
            try{
                base.states.filter { it.name != base.initialState }.forEach{
                    automata.addState(it.name)
                }
                base.finalStates.forEach { name ->
                    automata.addFinalState(name)
                }
            }catch ( e : AutomataExeption ) {
                throw AutomataExeption("""
                    Phase - creating states - name : ${automata.name}
                        ${e.message}
                    => states list [${base.states.joinToString("; ") { it.name }}]
                """.trimIndent())

            }
        }

        private fun createTransition(automata: Automata, base: AutomataData){
            if ( automata.states.isEmpty() ){
                throw AutomataExeption("""
                    Phase - creating transitions - name : ${automata.name}
                        states list empty
                """.trimIndent())
            }
            try{
                base.states.forEach { stateData ->
                    stateData.transitions.forEach {
                        automata.addTransition(stateData.name, it.symbol, it.to)
                    }
                }
            }catch (e : AutomataExeption) {
                val message = e.message
                throw AutomataExeption(
                    """
                        Phase - creating transitions - name : ${automata.name} 
                            $message
                    """.trimIndent()
                )
            }
        }
    }

}