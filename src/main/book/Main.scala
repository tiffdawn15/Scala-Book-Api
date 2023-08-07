package book

import book.smithy._
import book.controllers._
import book.db._
import book._

import cats.effect._
import cats.implicits._
import com.comcast.ip4s._
import java.util.concurrent.TimeUnit
import org.http4s._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.middleware._
import scala.concurrent.duration._
import smithy4s.http4s.SimpleRestJsonBuilder

object HelloWorldImpl extends HelloWorldService[IO] {
  def hello(name: String, town: Option[String]): IO[Greeting] = IO.pure {
    town match {
      case None    => Greeting(s"Hello $name!")
      case Some(t) => Greeting(s"Hello $name from $t!")
    }
  }
}

// object BookImpl extends BookApi[IO] {
//   // def getBooks(): IO[String] = IO.pure {

//   // }
// }

// TODO: What to do with routes from documentation
// object Routes {
//   // This can be easily mounted onto a server.
//   val myRoutes: Resource[IO, HttpRoutes[IO]] =
//     SimpleRestJsonBuilder.routes(HelloWorldImpl).resource

//   private val docs: HttpRoutes[IO] =
//     smithy4s.http4s.swagger.docs[IO](HelloWorldService)

//      val all: Resource[IO, HttpRoutes[IO]] = myRoutes.map(_ <+> docs)
// }

object Routes {
  val ENV = AppConfig.env
  val bookDb = BookDBConn.from(ENV)
  val bookDAO = BookDAO(bookDb)

  val bookService = BookService(bookDAO)

  val rateLimitMaxCalls: Int = ENV.get("RATE_LIMIT_MAX_CALLS").get.toInt
  val rateLimitWindow: Duration = {
    val length: Int = ENV.get("RATE_LIMIT_WINDOW_LENGTH").get.toInt
    val unit: String = ENV.get("RATE_LIMIT_WINDOW_UNIT").get.toUpperCase
    Duration(length, TimeUnit.valueOf(unit))
  }

  def getAll(local: IOLocal[Option[RequestContext]]): Resource[IO, HttpRoutes[IO]] = {
    given getRequestContext: IO[RequestContext] = local.get.flapMap {
      case Some(context) => IO.pure(context)
      case None => 
        IO.raiseError(
          new IllegalAccessException("Tried to acces the Request Context outside of the lifecycle of an http request")
        )
    }

    val authMiddleware = NewAuthMiddleware(local, bookDAO)

    for {
      books <- SimpleRestJsonBuilder.routes(BookController()).resource
    } yield openRoutes <+> securedRoutes
  }

}

object Main extends IOApp.Simple {
  
  def runs(args: List[String]) = {
    val logProfile = AppLogging.setProfile(Routes.ENV.get("LOG_PROFILE"))

    IOLocal(Option.empty[RequestContext]).flatMap { local => 
      Routes
      .getAll(local)
      .flatMap { rpites => 
        EmberServerBuilder
          .default[IO]
           .withHost(host"localhost")
           .withPort(port"9000")
          .withHttpApp(routes.orNotFound)
          .build
      }
         .use(_ => IO.never)
      }

    }
  }


  // val run = Routes.all
  //   .flatMap { routes =>
  //     EmberServerBuilder
  //       .default[IO]
  //       .withPort(port"9000")
  //       .withHost(host"localhost")
  //       .withHttpApp(routes.orNotFound)
  //       .build
  //   }
 

// TODO: This compiles but probably isn't what I need
  // def run: IO[Unit] =
  // EmberServerBuilder
  //   .default[IO]
  //   .build
  //   .evalMap(srv => IO.println(srv.addressIp4s))
  //   .useForever


