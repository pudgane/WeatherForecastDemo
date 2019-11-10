package com.pudagane.weatherforecast.network

import com.pudagane.weatherforecast.model.ForecastResponse
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @GET("daily")
    fun getReport(@Query("city") city:String,
                  @Query("key") key:String ): Call<ForecastResponse>

}