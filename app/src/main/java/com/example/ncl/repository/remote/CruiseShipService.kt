package com.example.ncl.repository.remote

import com.example.ncl.models.CruiseShip
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CruiseShipService {

    @GET("cms-service/cruise-ships/{cruiseShip}")
    suspend fun getCruiseShipInfo(@Path("cruiseShip") cruiseShip: String): Response<CruiseShip>
}