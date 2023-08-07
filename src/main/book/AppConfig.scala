package book

import scala.io.Source

object AppConfig {
    val env: Map[String, String] = {
        val localEnv = 
            try {
                val inputStream = getClass().getClassLoader().getResourceAsStream(".env")
                Source
                .fromInputStream(inputStream)
                .getLines
                .map(_.split("="))
                .collect { case Array(key, value) if !key.startsWith("#") => (key, value) }
            .toMap
        } catch {
            // TODO: Double check what type of errors could be thrown here
            case ex:IllegalStateException => Map()
            
        }
        sys.env ++ localEnv
    
    }
}