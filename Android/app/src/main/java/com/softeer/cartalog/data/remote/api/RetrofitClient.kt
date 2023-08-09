package com.softeer.cartalog.data.remote.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://3.36.126.30/"

    private val gson = GsonBuilder()
        .create()

    private val client = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = client.create(ApiService::class.java)

}