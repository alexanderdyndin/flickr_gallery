package com.example.feature.gallery.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Photo
import com.example.feature.R

class PhotoAdapter(
    private val listener: (Photo) -> Unit
): PagingDataAdapter<Photo, PhotoViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            holder.clickListener = { listener.invoke(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.photo_viewholder, parent, false)
        )
    }

    override fun onViewRecycled(holder: PhotoViewHolder) {
        holder.recycle()
    }
}