package com.example.feature.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.model.Photo
import com.example.feature.R
import com.example.feature.databinding.FragmentGalleryBinding
import com.example.feature.gallery.list.PhotoAdapter
import com.example.feature.photo.PhotoFragment
import com.example.feature.utils.LoadAdapter
import com.example.feature.utils.hide
import com.example.feature.utils.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment: Fragment(R.layout.fragment_gallery) {

    private val viewBinding by viewBinding(FragmentGalleryBinding::bind)
    private val viewModel: GalleryViewModel by viewModel()

    private val adapter by lazy {
        PhotoAdapter(::openPhoto)
            .apply { stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY }
    }
    private val loadAdapter by lazy { LoadAdapter { this@GalleryFragment.adapter.retry() } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when(it.refresh) {
                    is LoadState.NotLoading -> {
                        viewBinding.header.progress.hide()
                        viewBinding.header.error.hide()
                    }
                    LoadState.Loading -> {
                        viewBinding.header.error.hide()
                        viewBinding.header.progress.show()
                    }
                    is LoadState.Error -> {
                        viewBinding.header.progress.hide()
                        viewBinding.header.error.show()
                    }
                }
            }
        }
        viewBinding.header.retryButton.setOnClickListener { adapter.retry() }
        viewBinding.recyclerPhoto.adapter = adapter.withLoadStateFooter(loadAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.recyclerPhoto.adapter = null
    }

    private fun openPhoto(photo: Photo) {
        val fragment = PhotoFragment.createInstance(photo)
        parentFragmentManager.beginTransaction()
            .hide(this)
            .add(R.id.fragment_container_view, fragment)
            .addToBackStack("photo")
            .commitAllowingStateLoss()
    }
}