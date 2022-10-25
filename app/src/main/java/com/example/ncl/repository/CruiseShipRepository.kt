package com.example.ncl.repository

import com.example.ncl.models.CruiseShip
import kotlinx.coroutines.flow.Flow

interface CruiseShipRepository {
    suspend fun getCruiseShipInfo(cruiseShip: String): Flow<NetworkState<CruiseShip>>
}