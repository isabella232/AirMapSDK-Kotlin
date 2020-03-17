package com.airmap.airmapsdk.models

import com.squareup.moshi.Json

/*
TODO: Make data classes for all the Error response types
Encountered Error Responses:

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

(Code 400)
{
	"status": "fail",
	"data": {
		"message": "failed to create flight plan"
	}
}

(Code 401)
{
	"msg": "authentication failed"
}

(Status 500)
(Status 403)
{
	"status": "error",
	"message": "failed to create flight plan"
}

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

(Code 405)
<Empty Response>

(Code 504)
upstream request timeout
 */

data class Error(
    @Json(name = "name") val name: String?,
    @Json(name = "message") val message: String?
)
