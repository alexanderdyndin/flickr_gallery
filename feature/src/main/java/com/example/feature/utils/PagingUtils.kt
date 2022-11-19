package com.example.feature.utils

import androidx.paging.PagingConfig
import kotlin.math.roundToInt

fun getPagingConfig(pageSize: Int) = PagingConfig(
    pageSize = pageSize,
    prefetchDistance = (pageSize * 0.75).roundToInt(),
    enablePlaceholders = false,
    initialLoadSize = (pageSize * 1.5).roundToInt(),
)