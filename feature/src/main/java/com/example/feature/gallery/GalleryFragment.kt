package com.example.feature.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.model.Photo
import com.example.feature.R
import com.example.feature.databinding.FragmentGalleryBinding
import com.example.feature.gallery.list.PhotoAdapter
import com.example.feature.photo.PhotoFragment
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment: Fragment(R.layout.fragment_gallery) {

    companion object {

    }

    private val viewBinding by viewBinding(FragmentGalleryBinding::bind)
    private val viewModel: GalleryViewModel by viewModel()

    private val adapter by lazy {
        PhotoAdapter(::openPhoto).apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.photos.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.recyclerPhoto.apply {
            adapter = this@GalleryFragment.adapter
        }
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
            .commit()
    }
}