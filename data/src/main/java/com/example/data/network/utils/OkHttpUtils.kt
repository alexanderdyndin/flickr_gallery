package com.example.data.network.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

fun createOkHttp() = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .addInterceptor(createLoggerInterceptor())
    .addInterceptor(createAuthInterceptor())
    .build()

private fun createLoggerInterceptor() =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

private const val API_KEY_HEADER = "api_key"
private const val API_KEY = "2b3eab89502f724276e364a119c5c7f0"

private const val FORMAT_HEADER = "format"
private const val FORMAT = "json"

private const val JSON_CALLBACK_HEADER = "nojsoncallback"
private const val JSON_VALUE = "1"

private fun createAuthInterceptor() = Interceptor { chain ->
    val originalRequest = chain.request()

    val newUrl = originalRequest.url
        .newBuilder()
        .addQueryParameter(API_KEY_HEADER, API_KEY)
        .addQueryParameter(FORMAT_HEADER, FORMAT)
        .addQueryParameter(FORMAT_HEADER, FORMAT)
        .addQueryParameter(JSON_CALLBACK_HEADER, JSON_VALUE)
        .build()

    val newRequest = originalRequest.newBuilder()
        .url(newUrl)
        .build()

    chain.proceed(newRequest)
}