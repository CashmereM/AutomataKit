package e222531G.automata.kit.models

import e222531G.automata.kit.exceptions.AutomatonException
import kotlinx.serialization.*

@Serializable
data class AutomatonData(
    val name: String,
    val description: String,
    val alphabet: Set<String>,
    val states: List<StateData>,
    val initialState: String,
    val finalStates: List<String>

){
    init {
        if ( alphabet.distinct().size != alphabet.size ){
            throw AutomatonException("The AutomatonData instance contains duplicate characters in the alphabet.")
        }
    }
}

