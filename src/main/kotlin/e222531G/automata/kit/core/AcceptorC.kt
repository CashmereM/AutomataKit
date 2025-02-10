package e222531G.automata.kit.core

class AcceptorC : AbstractAcceptor() {

    override fun accepts(
        expression: String,
        initialState: State,
        states: Map<String, State>,
        finalStates: List<State>
    ): Boolean {
        TODO()
    }
}

// Faire un accepteur qui prend une expression dont les caracteres sont séparés par un séparateur ( '.', ' ' )
// param : separateur : Boolean ou Char ou String