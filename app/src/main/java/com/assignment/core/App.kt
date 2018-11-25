package com.assignment.core

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.assignment.data.network.ApiService
import com.assignment.data.network.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class App  : Application() {

    companion object {
        private val TAG = App::class.java.simpleName
        var instance: App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}