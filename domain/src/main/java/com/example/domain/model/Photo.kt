package com.example.domain.model

data class Photo(
    val id: String,
    val title: String,
    val url: String,
    val width: Int,
    val height: Int
): java.io.Serializable
