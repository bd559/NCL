package com.example.ncl.repository

import com.example.ncl.repository.remote.CruiseShipService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Exception

class CruiseShipRepositoryImpl @Inject constructor(
    private val cruiseShipService: CruiseShipService
) : CruiseShipRepository {
    override suspend fun getCruiseShipInfo(cruiseShip: String) = flow {
        emit(NetworkState.Loading)
        val result = try {
            val response = cruiseShipService.getCruiseShipInfo(cruiseShip)
            if (response.isSuccessful && response.body() != null) {
                NetworkState.Success(response.body()!!)
            } else {
                NetworkState.Failure("Unable to get ship")
            }
        } catch (ex: Exception) {
            NetworkState.Failure("${ex.message}")
        }
        emit(result)
    }
}



sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Failure<T>(val message: String) : NetworkState<T>()
}
