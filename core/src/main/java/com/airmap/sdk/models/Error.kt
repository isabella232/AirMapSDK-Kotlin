package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Various examples of error response formats we've seen from the API
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

/*
(Code 400)
{
	"status": "fail",
	"data": {
		"message": "failed to create flight plan"
	}
}
 */

/*
(Code 401)
{
	"msg": "authentication failed"
}
 */

/*
(Status 500)
(Status 403)
{
	"status": "error",
	"message": "failed to create flight plan"
}
 */

/*
(Code 405)
<Empty Response>
 */

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

/*
(Code 504)
upstream request timeout
 */

/*
TODO: How to handle the below response with the ErrorDetails class? "data" here is a string instead
  of an object :(
  Potential solution: Have multiple data classes and greedily attempt to parse as each one until
  it succeds (and make them part of a sealed class?)
  (see: https://github.com/airmap/AirMapSDK-Kotlin/blob/caab5d/core/src/main/java/com/airmap/sdk/models/Error.kt)
(Code 404)
{"status":"fail","data":"No airspace found."}
 */

/**
 * This class will wrap an error returned by the server. It will contain the response [code] and
 * some [details] around what may have gone wrong
 */
@Suppress("MemberVisibilityCanBePrivate")
class ServerError(val code: Int, val details: ErrorDetails) :
    Exception("($code) ${details.displayMessage}")

/**
 * We attempt to consolidate all the different error formats into one class. Not all of the fields
 * here will have meaning. Use [displayMessage] for something more tangible. The raw response is
 * contained in [raw]. Warning: sometimes the API returns raw HTML, which can lead to a long [raw]
 * response body. When accessing [raw], be sure to check its size before usage
 */
@JsonClass(generateAdapter = true)
data class ErrorDetails(
    @Json(name = "status") val status: String = "",
    @Json(name = "data") val data: Data = Data(),
    @Json(name = "msg") val msg: String = "",
    @Json(name = "message") val message: String = "",
    @Transient val raw: String = "",
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "message") val message: String = "",
        @Json(name = "errors") val errors: List<Error> = listOf(),
    )

    @JsonClass(generateAdapter = true)
    data class Error(
        @Json(name = "name") val name: String = "",
        @Json(name = "message") val message: String = "",
    )

    val displayMessage = (listOf(status, msg, message, data.message) +
        data.errors.map { "${it.name}: ${it.message}" })
        .map { it.trim(',', ':', '.', ' ') }
        .filter { it.isNotEmpty() }
        .ifEmpty { if (raw.isNotBlank()) listOf(raw) else listOf("Unknown error") }
        .joinToString(". ")
}
