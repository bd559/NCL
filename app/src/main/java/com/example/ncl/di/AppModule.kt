package com.example.ncl.di

import com.example.ncl.repository.remote.CruiseShipService
import com.example.ncl.repository.CruiseShipRepositoryImpl
import com.example.ncl.repository.CruiseShipRepository
import com.example.ncl.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesCruiseShipRepository(
        cruiseShipService: CruiseShipService
    ): CruiseShipRepository {
        return CruiseShipRepositoryImpl(cruiseShipService)
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }

    @Singleton
    @Provides
    fun providesCruiseShipService(retrofit: Retrofit): CruiseShipService {
        return retrofit.create(CruiseShipService::class.java)
    }
}
