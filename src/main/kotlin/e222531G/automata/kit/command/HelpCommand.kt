package e222531G.automata.kit.command

import java.io.BufferedReader


class HelpCommand() : ICommand {
    private val commands: MutableMap<String, String> = mutableMapOf(
        "analyze" to "- Analyzes a given expression to determine whether it is accepted or rejected by the current automaton.",
        "describe" to "- Displays a detailed description of the automaton, including its states, transitions, and operational rules.",
        "export" to "- Exports the current automaton to a .dot file for graphical visualization or further processing.",
        "clear" to " - Clears the terminal screen, removing all previously displayed text.",
        "exit"  to " - Exit the application ( take no arguments ) "
    )

    override fun parseArgs(args: Array<String>) {
        // No specific arguments are needed for the HelpCommand.
        if (args.isNotEmpty()) {
            throw Exception("`help` command does not take any arguments.")
        }
    }

    override fun execute(args: Array<String>) {
        parseArgs(args) // Ensure no arguments were passed
        println("Available commands:")
        for ((command, description) in commands) {
            println("\n$command :\n$description")
        }
        print("\n")
    }

}