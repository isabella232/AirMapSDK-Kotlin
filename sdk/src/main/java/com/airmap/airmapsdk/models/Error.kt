package com.airmap.airmapsdk.models

import com.squareup.moshi.Json

/*
TODO: Make data classes for all the Error response types
Encountered Error Responses:

(Code 401)
{
	"msg": "authentication failed"
}

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

(Status 500)
(Status 403)
{
	"status": "error",
	"message": "failed to create flight plan"
}

(Code 504)
upstream request timeout

(Code 405)
<Empty Response>
 */

data class Error(
    @Json(name = "name") val name: String?,
    @Json(name = "message") val message: String?
)
