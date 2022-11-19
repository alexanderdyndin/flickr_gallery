package com.example.data.network.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Page(
    val page: Int,
    val pages: Int,
    @SerialName("photo")
    val data: List<Photo>
)

fun Page.toModel() : com.example.domain.model.PhotoPage {
    return com.example.domain.model.PhotoPage(page, pages, data.map { it.toModel() })
}
