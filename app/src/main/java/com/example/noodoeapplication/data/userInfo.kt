package com.example.noodoeapplication.data

import com.squareup.moshi.Json

data class UserInfo(
    @field:Json(name = "objectId") val objectId: String,
    @field:Json(name = "reportEmail") val reportEmail: String,
    @field:Json(name = "timezone") val timezone: String,
    @field:Json(name = "sessionToken") val sessionToken: String
)