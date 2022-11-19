package com.example.feature.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.feature.R
import com.example.feature.databinding.FooterLyaoutBinding

class LoadAdapter(private val retryListener: () -> Unit) :
    LoadStateAdapter<LoadAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        when (loadState) {
            is LoadState.NotLoading -> holder.hideAll()
            LoadState.Loading -> holder.bindLoading()
            is LoadState.Error -> holder.bindError(retryListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.footer_lyaout, parent, false)
        )
    }

    class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val binding by viewBinding(FooterLyaoutBinding::bind)

        fun hideAll() {
            binding.error.hide()
            binding.progress.hide()
        }

        fun bindLoading() {
            binding.progress.show()
            binding.error.hide()
        }

        fun bindError(retryListener: () -> Unit) {
            binding.progress.hide()
            binding.retryButton.setOnClickListener { retryListener.invoke() }
            binding.error.show()
        }
    }
}