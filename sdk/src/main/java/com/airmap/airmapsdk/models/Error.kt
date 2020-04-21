package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO: Parse the actual error string according to the models below
class ServerError(error: String) : Exception(error)
sealed class Error // TODO

// TODO: Make Retrofit use this

/*
(Code 400)
{
  "status": "fail",
  "data": {
    "errors": [
      {
        "name": "ids",
        "message": "Must include a comma seperated list of uuids"
      }
    ]
  }
}
 */
@JsonClass(generateAdapter = true)
data class Error_400_1(
    @Json(name = "status") val status: String = "",
    @Json(name = "data") val data: Data = Data(),
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "errors") val errors: List<Error> = listOf(),
    )

    @JsonClass(generateAdapter = true)
    data class Error(
        @Json(name = "name") val name: String = "",
        @Json(name = "message") val message: String = "",
    )
}

/*
(Code 400)
{
	"status": "fail",
	"data": {
		"message": "failed to create flight plan"
	}
}
 */
@JsonClass(generateAdapter = true)
data class Error_400_2(
    @Json(name = "status") val status: String = "",
    @Json(name = "data") val data: Data = Data(),
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "message") val message: String = "",
    )
}

/*
(Code 401)
{
	"msg": "authentication failed"
}
 */
@JsonClass(generateAdapter = true)
data class Error_401(
    @Json(name = "msg") val msg: String = "",
)

/*
(Status 500)
(Status 403)
{
	"status": "error",
	"message": "failed to create flight plan"
}
 */
@JsonClass(generateAdapter = true)
data class Error_403_500(
    @Json(name = "status") val status: String = "",
    @Json(name = "message") val message: String = "",
)

/*
(Code 405)
<Empty Response>
 */
typealias Error_405 = Unit

/*
(Code 404)
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Error</title>
</head>
<body>
<pre>Cannot GET /</pre>
</body>
</html>
 */
typealias Error_404 = String

/*
(Code 504)
upstream request timeout
 */
typealias Error_504 = String
