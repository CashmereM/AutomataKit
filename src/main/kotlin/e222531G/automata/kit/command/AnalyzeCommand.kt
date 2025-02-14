package e222531G.automata.kit.command

import e222531G.automata.kit.core.Automaton
import e222531G.automata.kit.exceptions.AutomatonException
import kotlin.math.exp

class AnalyzeCommand : ICommand {

    private val RESET = "\u001B[0m"
    private val YELLOW = "\u001B[33m"
    private val RED = "\u001B[31m"

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
            println("--------------- My TP Menu -------------------------")

            availableAutomatons.forEachIndexed { index, automaton ->
                println("${index + 1}. ${automaton.name} ${automaton.format}")
            }
            println("99. Exit the application")
            print("Your choice (1-99) ? ")

            val choice = readlnOrNull()?.toIntOrNull()

            when {
                choice == null -> {
                    println("Please enter a value.")
                }
                choice == 99 -> {
                    println("Stopping application. Thanks !")
                    break
                }
                choice in 1..availableAutomatons.size -> {
                    val selectedAutomaton = availableAutomatons[choice - 1]
                    println("You selected: ${selectedAutomaton.name}")
                    var expression: String?
                    do {
                        println("${YELLOW}Please enter the string to analyze:$RESET")
                        print("--> ")
                        expression = readlnOrNull()?.trim()

                        if (expression.isNullOrEmpty()) {
                            println("${RED}Invalid input. Please enter a non-empty expression.$RESET")
                        }
                    } while (expression.isNullOrEmpty())

                    val result = selectedAutomaton.accepts(expression)
                    if ( result ){
                        println("Result : OK")
                    }else{
                        println("Result : KO")
                    }

                }
                else -> {
                    println("Invalid choice, please try again.")
                }
            }
            println("-------------------------------------------------------")
        }
    }
}