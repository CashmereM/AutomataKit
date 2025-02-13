package e222531G.automata.kit.command

class CommandProcessor private constructor(){
    private val commands = mutableMapOf<String, ICommand>()


    init {
        // Save the available commands
        commands["exit"] = ExitCommand()
        commands["clear"] = ClearCommand()
        commands["help"] = HelpCommand()
        commands["export"] = ExportCommand()
    }

    fun processCommand(input: String) {
        val parts = input.split(" ")
        val commandName = parts[0]
        val args = parts.drop(1).filter { it != "" }.toTypedArray()

        val command = commands[commandName]
        if (command != null) {
            try{
                command.execute(args)
            }catch(e : Exception){
                val redBold = "\u001B[31;1m"
                val yellow = "\u001B[33m"
                val reset = "\u001B[0m"

                println("$redBold[Exception]$reset $yellow${e.message}$reset")
            }
        } else {
            println("Unknown command : $commandName")
        }
    }

    companion object{
        val INSTANCE : CommandProcessor by lazy {
            CommandProcessor()
        }
    }
}
