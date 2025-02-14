package e222531G.automata.kit.command

import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.exceptions.AutomatonException
import e222531G.automata.kit.utils.DotExporter

class ExportCommand : ICommand {

    private val exporter = DotExporter()

    override fun parseArgs(args: Array<String>) {
        if (args.size > 1) {
            throw IllegalArgumentException("Export command : too much value")
        }
    }

    override fun execute(args: Array<String>) {
        parseArgs(args)
        displayMenu()
    }

    private fun displayMenu() {
        val availableAutomatons = Automaton.getAutomatons()
        if (availableAutomatons.isEmpty()) {
            throw AutomatonException("No automatons available")
        }
        while (true) {
            println("--------------- Exports Menu -------------------------")

            availableAutomatons.forEachIndexed { index, automaton ->
                println("${index + 1}. ${automaton.name} ${automaton.format}")
            }
            println("99. Exit the application")
            print("Your choice (1-99) ? ")

            val choice = readlnOrNull()?.toIntOrNull()

            when (choice) {
                null -> {
                    println("Please enter a value.")
                }
                99 -> {
                    println("Stopping application. Thanks !")
                    break
                }
                in 1..availableAutomatons.size -> {
                    val selectedAutomaton = availableAutomatons[choice - 1]
                    println("You selected: ${selectedAutomaton.name}")
                    exporter.export(selectedAutomaton.getBase())
                }
                else -> {
                    println("Invalid choice, please try again.")
                }
            }
            println("-------------------------------------------------------")
        }
    }
}