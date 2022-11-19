package com.example.data.network.utils

import kotlinx.serialization.json.Json

fun createJson() = Json {
    ignoreUnknownKeys = true
    allowStructuredMapKeys = true
}