package com.example.feature.gallery.list

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.imageLoader
import coil.request.Disposable
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.example.domain.model.Photo
import com.example.feature.R
import com.example.feature.databinding.PhotoViewholderBinding
import com.example.feature.utils.*

class PhotoViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    var clickListener: (() -> Unit)? = null

    init {
        view.setOnClickListener { clickListener?.invoke() }
    }

    private val binding by viewBinding(PhotoViewholderBinding::bind)

    private var imageDisposable: Disposable? = null

    fun bind(photo: Photo) {
        imageDisposable?.dispose()
        binding.image.apply {
            show()
            resizeRequest(photo.width, photo.height)
        }
        imageDisposable = ImageRequest.Builder(view.context)
            .allowHardware(true)
            .data(photo.url)
            .error(R.drawable.ic_launcher_background)
            .target(binding.image)
            .listener(object : ImageRequest.Listener {
                override fun onError(request: ImageRequest, result: ErrorResult) {
                    binding.image.hide()
                }
            })
            .build()
            .let(view.context.imageLoader::enqueue)
    }

    fun recycle() {
        imageDisposable?.dispose()
        binding.image.setImageResource(0)
        clickListener = null
    }
}