package com.example.data.network.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit

const val URL = "https://www.flickr.com/services/rest/"

@Suppress("DEPRECATION")
inline fun <reified T> createApi(
    okHttpClient: OkHttpClient,
    converterFactory: JsonConverterFactory,
    url: String
): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
        .create(T::class.java)
}