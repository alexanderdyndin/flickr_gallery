package com.example.data

import com.example.data.network.api.FlickrApi
import com.example.data.network.utils.createApi
import com.example.data.network.utils.createOkHttp
import com.example.data.network.utils.JsonConverterFactory
import com.example.data.network.utils.URL
import com.example.data.network.utils.createJson
import com.example.data.repository.PhotoRepository
import com.example.domain.repository.IPhotoRepository
import org.koin.dsl.module

val dataModule = module {
    single { createOkHttp() }

    single { createJson() }
    single { JsonConverterFactory(get()) }

    single<FlickrApi> { createApi(get(), get(), URL) }

    single<IPhotoRepository> { PhotoRepository(get()) }

}