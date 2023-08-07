package book

import book.smithy._

import cats.effect._
import cats.implicits._

import org.http4s.implicits._
import org.http4s.ember.server._
import org.http4s._


import smithy4s.http4s.SimpleRestJsonBuilder

object HelloWorldImpl extends HelloWorldService[IO] {
  def hello(name: String, town: Option[String]): IO[Greeting] = IO.pure {
    town match {
      case None => Greeting(s"Hello $name!")
      case Some(t) => Greeting(s"Hello $name from $t!")
    }
  }
}


// object BookImpl extends BookApi[IO] {
//   // def getBooks(): IO[String] = IO.pure {
  
//   // }
// }

object Routes {
  // This can be easily mounted onto a server.
  val myRoutes: Resource[IO, HttpRoutes[IO]] =
    SimpleRestJsonBuilder.routes(HelloWorldImpl).resource

  private val docs: HttpRoutes[IO] =
    smithy4s.http4s.swagger.docs[IO](HelloWorldService)

     val all: Resource[IO, HttpRoutes[IO]] = myRoutes.map(_ <+> docs)
}


object Main extends IOApp.Simple {


  def run: IO[Unit] =
      EmberServerBuilder
        .default[IO]
        .build
        .evalMap(srv => IO.println(srv.addressIp4s))
        .useForever
}
