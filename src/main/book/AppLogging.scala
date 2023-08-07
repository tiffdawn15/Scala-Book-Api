package book

import com.tersesystems.blindsight.{Logger, LoggerFactory}
import org.slf4j.event.Level

case class RequestContext(traceId: String, corpId: String, endpoint: String)
object RequestContext {
    val empty = RequestContext("", "", "")
}

trait AppLogging { 
    val logger = AppLogger(LoggerFactory.getLogger)
}

object AppLogging {
    // import ch.qos.logback.classic.{Level, Logger}
    import org.slf4j.LoggerFactory

    private enum Profile { case DEFAULT, LOCAL, DEV}

    private val defaultLevels = Map(
        "root" -> Level.WARN, 
        "book" -> Level.DEBUG, 
        "org.http4s.ember" -> Level.INFO, 
        "org.mongodb.driver" -> Level.WARN

    )

    def setProfile(maybeProfile: Option[String]): String = {
        val profile = maybeProfile.map(_.toLowerCase) match {
      case Some("local" ) => Profile.LOCAL
        case Some("dev") => Profile.DEV
        case _ => Profile.DEFAULT
        }

        profile.toString
  
    }
}

sealed class AppLogger