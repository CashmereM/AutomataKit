package e222531G.automata.kit.utils

import e222531G.automata.kit.models.AutomatonData
import java.io.File

class DotExporter {

    fun export(automatonData: AutomatonData, outputDir : String = "./"){
        val directory = File(outputDir)
        if (!directory.exists() || !directory.isDirectory || outputDir.isEmpty()) {
            throw IllegalArgumentException("Le chemin spécifié '$outputDir' n'est pas un répertoire valide.")
        }
        val graph = automatonData.states.joinToString("\n") { state ->
            val groupedTransitions = state.transitions.groupBy { it.to }
            groupedTransitions.entries.joinToString("\n") { (to, transitions) ->
                val symbols = transitions.joinToString(", ") { it.symbol }
                "${state.name} -> $to [label=\"$symbols\"]"
            }
        }
        val text = """
            digraph date {
            rankdir=LR;
            node [shape = doublecircle]; ${automatonData.finalStates.joinToString { finalState -> "$finalState " }};
            node [shape = circle];
            initial [shape=point];
            initial -> ${automatonData.initialState};
            start [shape=none,label=""];
            $graph
            }
        """.replace("   ", "")
        val outputFile = File(outputDir, "automaton.dot")
        outputFile.writeText(text)
        println("Fichier 'automaton.dot' créé dans le répertoire : ${outputFile.absolutePath}")
    }
}