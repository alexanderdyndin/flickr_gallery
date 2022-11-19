package com.example.feature

import com.example.feature.gallery.GalleryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureModule = module {
    viewModelOf(::GalleryViewModel)
}