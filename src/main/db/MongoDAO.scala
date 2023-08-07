package book.db

trait MongoDAO {
    val collectionName: String
    val mongoClient: Resource[cats.effect.IO, MongoClient[cats.effect.IO]]
}

class BookDAO(dbConn: BookDBConn) extends MongoDAO {
    val collectionName = "books"
    val mongoClient = dbConn.connection.client


    // TODO: validation
}
