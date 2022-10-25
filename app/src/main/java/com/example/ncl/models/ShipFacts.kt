package com.example.ncl.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShipFacts(
    val crew: String,
    val inauguralDate: String,
    val passengerCapacity: String,
)