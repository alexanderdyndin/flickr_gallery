package com.example.data.repository

import com.example.data.network.api.FlickrApi
import com.example.data.network.model.toModel
import com.example.domain.model.PhotoPage
import com.example.domain.model.Photo
import com.example.domain.repository.IPhotoRepository

class PhotoRepository(private val api: FlickrApi) : IPhotoRepository {

    companion object {
        private const val EXTRA_URL = "url_m"
    }

    override suspend fun getPhotos(page: Int, pageSize: Int) : PhotoPage {
        return api.getPhotoList(page, pageSize, listOf(EXTRA_URL))
            .page
            .toModel()
    }

}