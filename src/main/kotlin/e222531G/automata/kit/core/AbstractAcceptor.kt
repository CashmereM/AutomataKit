package e222531G.automata.kit.core

abstract class AbstractAcceptor {

    abstract fun accepts(
        expression : String,
        initialState : State,
        states : Map<String, State>,
        finalStates : List<State>
    ) : Boolean

}