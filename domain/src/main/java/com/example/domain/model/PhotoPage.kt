package com.example.domain.model

data class PhotoPage(
    val page: Int,
    val pagesCount: Int,
    val data: List<Photo>
)
