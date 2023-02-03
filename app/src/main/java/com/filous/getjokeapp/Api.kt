package com.filous.getjokeapp

import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("joke/Any?format=json&?type=single")
    fun getRandomData(): Call<DataType>

    @GET("joke/Programming?format=json&?type=single")
    fun getGeekData(): Call<DataType>

    @GET("joke/Dark?format=json&?type=single")
    fun getDarkData(): Call<DataType>
}