package e222531G.automata.kit.data

import kotlinx.serialization.*

@Serializable
data class AutomataData(
    val name: String,
    val description: String,
    val alphabet: List<String>,
    val states: List<StateData>,
    val initialState: String,
    val finalStates: List<String>

)

