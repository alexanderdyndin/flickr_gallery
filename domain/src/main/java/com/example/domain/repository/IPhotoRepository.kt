package com.example.domain.repository

import com.example.domain.model.PhotoPage
import com.example.domain.model.Photo

interface IPhotoRepository {

    suspend fun getPhotos(page: Int, pageSize: Int) : PhotoPage

}