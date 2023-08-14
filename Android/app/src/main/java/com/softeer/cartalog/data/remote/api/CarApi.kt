package com.softeer.cartalog.data.remote.api

import com.softeer.cartalog.data.model.Types
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface CarApi {

    @GET("models/1/types")
    suspend fun getTypes(
    ): Response<Types>

}