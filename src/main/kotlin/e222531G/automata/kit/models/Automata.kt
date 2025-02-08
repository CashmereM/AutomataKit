package e222531G.automata.kit.models

import e222531G.automata.kit.data.AutomataData

class Automata private constructor(
    val name: String,
    private val initialState : State
) {
    var description: String = ""
    val alphabet: MutableList<String> = mutableListOf()
    private val states: MutableMap<String, State> = mutableMapOf()
    private val finalStates: MutableList<State> = mutableListOf()

    init {
        states[initialState.name] = initialState
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
        } else if ( !this.alphabet.contains(symbol)) {
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

    fun accepts(expression : String) : Boolean{
        var currentState : State = this.initialState
        var index = 0
        while (index < expression.length){

            val char = expression[index].toString()
            val nextState = currentState.findNextState(char)
            if ( nextState == null){
                return false
            }else{
                currentState = nextState
            }
            index++
        }
        return this.finalStates.contains(currentState) && index == expression.length
    }


    companion object{
        fun createAutomata( base : AutomataData ) : Automata{
            val initialState = State(base.initialState)
            val newAutomata = Automata(base.name, initialState)
            newAutomata.alphabet.addAll(base.alphabet)
            newAutomata.description = base.description

            try{
                base.states.filter { it.name != base.initialState }.forEach{
                    newAutomata.addState(it.name)
                }
                base.finalStates.forEach { name ->
                    newAutomata.addFinalState(name)
                }
            }catch ( e : AutomataExeption ) {
                throw AutomataExeption("""
                    Phase - creating states - name : ${newAutomata.name}
                        ${e.message}
                    => states list [${base.states.joinToString("; ") { it.name }}]
                """.trimIndent())

            }
            try{
                base.states.forEach { stateData ->
                    stateData.transitions.forEach {
                        newAutomata.addTransition(stateData.name, it.symbol, it.to)
                    }
                }
            }catch (e : AutomataExeption) {
                val message = e.message
                throw AutomataExeption(
                    """
                        Phase - creating transitions - name : ${newAutomata.name} 
                            $message
                    """.trimIndent()
                )
            }
            return newAutomata
        }
    }

}