package e222531G.automata.kit.data

import kotlinx.serialization.*

@Serializable
data class StateData(
    val name: String,
    val transitions: List<TransitionData>
)