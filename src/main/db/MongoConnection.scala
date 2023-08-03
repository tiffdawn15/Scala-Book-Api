package book.db

import cats.effect.IO
import cats.effect.kernel.Resource
import mongo4cats.client.MongoClient
import mongo4cats.models.client.{MongoConnection, MongoConnectionType, MongoCredential}

sealed case class MongoDbConnection(
    val client: Resource[cats.effect.IO, MongoClient[cats.effect.IO]],
    val dbName: String
)

object MongoDbConnection {
    @throws[NoSuchElementException]
    def from(
        env: Map[String, String], 
        uriKey: String, 
        dbNameKey: String, 
        userNameKey: String, 
        passwordKey: String
    ) : MongoDbConnection = {
        val uri = "insert uri"
        val dbName = "books"

        val credential = for {
            username <- "tiffdawn15"
            password <- "password"
        } yield MongoCredential(username, password)

        val connection = uri.split("://") match {
            //Local
            case Array("mongodb", host) => MongoConnection.classic(host, 27017, credential)

            // Higher environements 
            case Array("mongodb+srv", host) => MongoConnection.srv(host, credential)

            case _ => 
                throw IllegalArgumentException("MongoDb uri must be mongodb://{host} or mongodb+srv://{host}, with no {port}")
        }

        val client = MongoClient.fromConnection[IO](connection)
        MongoDbConnection(client, dbName)
    }

    case class BookDBConn(connection: MongoDbConnection)

    object BookDBConn {
        val URI_KEY = "BOOK_MONGODB_URI"
        val DB_KEY = "BOOK_MONGDB_DATABASE"
        val USERNAME_KEY = "BOOK_MONGODB_USERNAME"
        val PASSWORD_KEY = "BOOK_MONGODB_PASSWORD"

        @throws[NoSuchElementException] 
        def from(env: Map[String, String] = Map.empty): BookDBConn = {
            val connection = MongoDbConnection.from(env, URI_KEY, DB_KEY, USERNAME_KEY, PASSWORD_KEY )
            BookDBConn(connection)
        }

    }
}