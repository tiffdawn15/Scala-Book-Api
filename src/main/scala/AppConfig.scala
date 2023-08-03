package book

import scala.io.Source

object AppConfig {
    val evn: Map[String, String] = {
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
            case ex => Map()
        }
        sys.env ++ localEnv
    
    }
}