package e222531G.automata.kit.utils

import e222531G.automata.kit.models.AutomatonData
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

class AutomatonReader {

    fun readAutomatonJsonFile( filePath : String ) : AutomatonData {
        val fileContent = object {}.javaClass.classLoader.getResource(filePath)?.readText() ?: throw FileNotFoundException(" The file '${filePath}'")
        val automatonData = Json.decodeFromString<AutomatonData>(fileContent)
        return automatonData
    }

    fun readAutomatonAuthFile( filePath : String )  {
        if ( !filePath.endsWith(".auth")){
            throw IllegalArgumentException("The file should have '.auth' extension have : $filePath")
        }
        val fileContent = object {}.javaClass.classLoader.getResource(filePath)?.readText() ?:
            throw FileNotFoundException(" The file '${filePath}' doesn't exists the resources directory")

    }
}