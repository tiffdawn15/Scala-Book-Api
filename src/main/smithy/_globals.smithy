$version: "2"
namespace book.smithy

@error("client")
@httpError(403)
structure ForbiddenError{}

@error("client")
@httpError(404)
structure NotFoundError {}

@error("client")
@httpError(409)
structure ConflictError {}

@error("client")
@httpError(429)
structure RateLimitError {}

@error("server")
@httpError(500)
structure InternalServiceErrror {}

list IntegerList {
    member: Integer
}

list StringList {
    member: String
}

@uniqueItems
list StringSet {
    member: String
}
