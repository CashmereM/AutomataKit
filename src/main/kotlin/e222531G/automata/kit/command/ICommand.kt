package e222531G.automata.kit.command

import java.io.BufferedReader

interface ICommand {
    private val helpRepositoryPath : String
        get() = "resources/helps/"

    /**
     * Reads a resource file located in the help directory.
     *
     * @param resourcePath The relative path of the resource file to be read.
     * @return The content of the file as a string.
     * @throws IllegalArgumentException If the file is not found.
     */
    private fun readResourceFile(resourcePath: String): String {
        // Get the resource file from the provided path.
        val inputStream = {}::class.java.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("File not found: $resourcePath")

        // Read the file and return its content as a string.
        return inputStream.bufferedReader().use(BufferedReader::readText)
    }

    /**
     * Parses the arguments passed to the command.
     *
     * This function must be implemented in the classes using this interface
     * to interpret and validate the arguments provided by the user.
     *
     * @param args An array of strings representing the command arguments.
     */
    fun parseArgs(args: Array<String>)

    /**
     * Executes the command with the provided arguments.
     *
     * Each class implementing this interface will define its own logic to execute
     * the command. Execution may include actions like displaying results, modifying data,
     * or interacting with other parts of the program.
     *
     * @param args An array of strings representing the arguments to be processed during execution.
     */
    fun execute(args: Array<String>)

    /**
     * Displays help specific to a given command.
     *
     * This function allows the user to see detailed information about the command.
     * It tries to read a help file associated with the command and displays its content.
     *
     * @param commandName The name of the command for which to display help.
     * @throws IllegalArgumentException If the help file associated with the command is not found.
     */
    fun help(commandName: String) {
        // Display the content of the help file corresponding to the command name.
        println(readResourceFile(helpRepositoryPath + "${commandName}-help.txt"))
    }
}