package com.example.myapplication.retrofit2

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitInterface {

    @POST("/select")
    fun select(): Call<JsonObject>


    @POST("/insert")
    fun insert(@Body json:JsonObject): Call<String>
}