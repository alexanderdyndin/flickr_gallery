package com.example.data.network.api

import com.example.data.network.model.Page
import com.example.data.network.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("?method=flickr.interestingness.getList")
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("extras") extras: List<String>?) : Response<Page>

}