package com.example.feature.gallery.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.Photo
import com.example.domain.repository.IPhotoRepository

class PhotoPaging(private val repository: IPhotoRepository, private val pageSize: Int) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
            ?.let(state::closestPageToPosition)
            ?.let { it.prevKey?.plus(1) ?: it.prevKey?.minus(1) }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val pageNumber = params.key ?: 1
            val response = repository.getPhotos(pageNumber, pageSize)
            val prevKey = if (pageNumber > 1) pageNumber - 1 else null
            val nextKey = if (response.page < response.pagesCount) pageNumber + 1 else null
            LoadResult.Page(
                data = response.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}