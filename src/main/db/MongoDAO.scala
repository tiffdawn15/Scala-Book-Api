package book.db

trait MongoDAO {
    val collectionName: String
    val mongoClient: Resource[cats.effect.IO, MongoClient[cats.effect.IO]]
}