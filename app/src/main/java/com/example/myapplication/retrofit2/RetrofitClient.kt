package com.example.myapplication.retrofit2

import android.util.Log
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    var url = "https://sleepy-lowlands-44732.herokuapp.com"

    private var retrofit : Retrofit? = null
    private var client = OkHttpClient()
    fun getResponse(): Retrofit? {

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                var requestOrigin = chain.request()



                var requestBuilder = requestOrigin.newBuilder()
                    .addHeader("h","h")
                    .method(requestOrigin.method(), requestOrigin.body())
                var request = requestBuilder.build()
                chain.proceed(request)
            }.build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        return retrofit

    }
}
