package com.softeer.cartalog.data.remote.api

import com.softeer.cartalog.data.model.Types
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarApi {

    @GET("models/1/types")
    suspend fun getTypes(
    ): Response<Types>
}