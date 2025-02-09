package e222531G.automata.kit.models

import kotlinx.serialization.Serializable

@Serializable
data class TransitionData(
    val symbol: String,
    val to: String
)