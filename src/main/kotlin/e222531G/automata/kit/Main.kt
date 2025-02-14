package e222531G.automata.kit

import e222531G.automata.kit.command.CommandProcessor
import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.scripts.InstantiateAutomatons
import e222531G.automata.kit.utils.AutomatonReader
import e222531G.automata.kit.utils.DotExporter

fun main() {
    val commandProcessor = CommandProcessor.INSTANCE
    InstantiateAutomatons().instantiate()

    val text = "Please enter a command : "
    commandProcessor.processCommand("clear")
    //println("\u001B[34mType 'help' to see available commands.\u001B[0m")
    // Attendre que l'utilisateur entre une commande
    while (true) {
        print("\u001B[32m${text}\u001B[0m")
        val input = readLine() ?: break
        commandProcessor.processCommand(input)
    }
}