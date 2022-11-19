package com.example.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.model.Photo
import com.example.domain.repository.IPhotoRepository
import com.example.feature.gallery.paging.PhotoPaging
import com.example.feature.utils.getPagingConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class GalleryViewModel(private val photoRepository: IPhotoRepository): ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
    }

    val photos: Flow<PagingData<Photo>> = Pager(
        config = getPagingConfig(PAGE_SIZE),
    ) {
        PhotoPaging(photoRepository, PAGE_SIZE)
    }
        .flow
        .cachedIn(viewModelScope)
}