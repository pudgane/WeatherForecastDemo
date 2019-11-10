package com.pudagane.weatherforecast.network

object ApiUtils {
    val BASE_URL = "https://api.weatherbit.io/v2.0/forecast/"

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)

}