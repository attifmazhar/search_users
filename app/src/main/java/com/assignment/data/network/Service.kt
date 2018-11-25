package com.assignment.data.network

import com.assignment.core.App
import com.assignment.core.Config
import com.assignment.data.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Service {

    /**
     * Connection timeout duration
     */
    val CONNECT_TIMEOUT : Long = 10 * 1000
    /**
     * Connection Read timeout duration
     */
    val READ_TIMEOUT : Long = 10 * 1000
    /**
     * Connection write timeout duration
     */
    val WRITE_TIMEOUT : Long = 10 * 1000

    var api: ApiService? = null

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        val client = httpClient.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(Config.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        api = retrofit.create(ApiService::class.java)
    }
}
