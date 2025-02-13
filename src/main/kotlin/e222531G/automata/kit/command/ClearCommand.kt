package e222531G.automata.kit.command

class ClearCommand : ICommand {
    private val os = System.getProperty("os.name").lowercase()
    override fun parseArgs(args: Array<String>) {
        return
    }

    override fun execute(args: Array<String>) {
        when {
            os.contains("win") -> {
                // Windows - use 'cls'
                ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
                println("Type 'help' to see available commands.")
            }
            os.contains("nix") || os.contains("nux") || os.contains("mac") -> {
                // Linux/macOS - use 'clear'
                ProcessBuilder("clear").inheritIO().start().waitFor()
                println("\u001B[34mType 'help' to see available commands.\u001B[0m")
            }
            else -> {
                println("Unsupported OS for terminal clear.")
            }
        }
    }
}