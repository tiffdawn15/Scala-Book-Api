$version: "2"
namespace book.smithy

use alloy#simpleRestJson

@simpleRestJson
@httpBearerAuth
service BookApi {
  version: "1.0.0",
  operations: [GetBooks]
  }

// GET books
@readonly
@http(method: "GET", uri: "/books", code: 200)
operation GetBooks {
  output: Get_Book_Output
}

structure Book {
  @required
  title: String
}

structure Get_Book_Output {
    @required
    data: BookList
}

list BookList { member: Book}