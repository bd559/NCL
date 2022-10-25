package com.example.ncl.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CruiseShip(
    val shipFacts: ShipFacts,
    val shipName: String,
)
