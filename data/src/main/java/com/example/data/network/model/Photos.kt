package com.example.data.network.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Photo(
    val id: String,
    val title: String,
    @SerialName("url_m")
    val url: String,
    @SerialName("width_m")
    val width: Int,
    @SerialName("height_m")
    val heigth: Int
)

fun Photo.toModel() : com.example.domain.model.Photo {
    return com.example.domain.model.Photo(id, title, url, width, heigth)
}
