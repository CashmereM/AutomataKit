package e222531G.automata.kit.command

import kotlin.system.exitProcess

class ExitCommand : ICommand {
    override fun parseArgs(args: Array<String>) {
        return
    }

    override fun execute(args: Array<String>) {
        exitProcess(1)
    }
}