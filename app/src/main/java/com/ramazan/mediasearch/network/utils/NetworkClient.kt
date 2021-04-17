package com.ramazan.mediasearch.network.utils

import com.google.gson.GsonBuilder
import com.ramazan.mediasearch.BuildConfig
import com.ramazan.mediasearch.network.services.SearchService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

object NetworkClient {

    /** Timeout values **/
    private const val CONNECT_TIMEOUT = 20L
    private const val READ_TIMEOUT = 120L
    private const val WRITE_TIMEOUT = 120L
    private const val RECONNECT_INTERVAL: Long = 10 * 1000


    private const val BASE_URL_SEARCH: String = BuildConfig.SEARCH_BASE_URL
    /** Provides Retrofit Interface **/
    private fun <T> provideService(
        client: OkHttpClient,
        baseUrl: String,
        clazz: Class<T>,
        scalars: Boolean = false
    ): T {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        val gson = gsonBuilder.create()

        val builder = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)

        if (scalars) {
            builder.addConverterFactory(ScalarsConverterFactory.create())
        } else {
            builder.addConverterFactory(GsonConverterFactory.create(gson))
        }

        builder.addConverterFactory(EnumConverterFactory())

        return builder.build().create(clazz)
    }
    fun provideSearchService(client: OkHttpClient) =
        provideService(
            client,
            BASE_URL_SEARCH,
            SearchService::class.java
        )
    fun provideClient(

    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }



}