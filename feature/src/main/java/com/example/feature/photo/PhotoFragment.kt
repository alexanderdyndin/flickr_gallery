package com.example.feature.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.imageLoader
import coil.request.Disposable
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.example.domain.model.Photo
import com.example.feature.R
import com.example.feature.databinding.FragmentPhotoBinding
import com.example.feature.utils.hide
import com.example.feature.utils.resizeRequest

class PhotoFragment: Fragment(R.layout.fragment_photo) {

    companion object {
        private const val PHOTO_ARG = "photo_arg"

        fun createInstance(photo: Photo) = PhotoFragment().apply {
            arguments = (arguments ?: Bundle()).apply {
                putSerializable(PHOTO_ARG, photo)
            }
        }
    }

    private val binding by viewBinding(FragmentPhotoBinding::bind)

    private lateinit var imageDisposable: Disposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = requireArguments().getSerializable(PHOTO_ARG) as Photo
        setImage(photo.url, photo.width, photo.height)
        updateName(photo.title)
        binding.toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun setImage(url: String, width: Int, height: Int) {
//        binding.image.resizeRequest(width, height)
        imageDisposable = ImageRequest.Builder(requireContext())
            .allowHardware(true)
            .data(url)
            .error(R.drawable.ic_launcher_background)
            .target(binding.image)
            .listener(object : ImageRequest.Listener {
                override fun onError(request: ImageRequest, result: ErrorResult) {
                    binding.image.hide()
                }
            })
            .build()
            .let(requireContext().imageLoader::enqueue)
    }

    private fun updateName(name: String) {
        binding.toolbar.title = name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageDisposable.dispose()
    }

}