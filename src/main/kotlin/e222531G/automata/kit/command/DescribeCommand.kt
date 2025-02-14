package e222531G.automata.kit.command

import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.exceptions.AutomatonException


class DescribeCommand() : ICommand {
    private val CYAN = "\u001B[36m"
    private val RESET = "\u001B[0m"
    override fun parseArgs(args: Array<String>) {
        if (args.isNotEmpty()) {
            throw Exception("`analyze` command does not take any arguments.")
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
            println("--------------- Automaton description Menu -------------------------")

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
                    println("Description : ")
                    printFormattedText("$CYAN${selectedAutomaton.description}$RESET")
                    println("Press enter to pass")
                    readlnOrNull()
                }
                else -> {
                    println("Invalid choice, please try again.")
                }
            }
            println("-------------------------------------------------------")
        }
    }

    private fun printFormattedText(text: String, maxWidth: Int = 80) {
        text.split(" ").fold("") { line, word ->
            if (line.length + word.length + 1 > maxWidth) {
                println(line)
                word
            } else {
                "$line $word"
            }
        }.also { println(it) }
    }

}