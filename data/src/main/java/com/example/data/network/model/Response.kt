package com.example.data.network.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Response<T>(
    @SerialName("photos")
    val page: T
)