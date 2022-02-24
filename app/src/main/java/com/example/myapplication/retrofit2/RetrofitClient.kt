package com.example.myapplication.retrofit2

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private var url = "http://localhost:8012"

    private var retrofit : Retrofit? = null
    private var client = OkHttpClient()
    fun getResponse(): Retrofit? {

        client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val requestOrigin = chain.request()



                val requestBuilder = requestOrigin.newBuilder()
                    .addHeader("h","h")
                    .method(requestOrigin.method(), requestOrigin.body())
                val request = requestBuilder.build()
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
