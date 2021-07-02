package com.perapps.testingpractice.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.perapps.testingpractice.R
import com.perapps.testingpractice.adapter.ImageAdapter
import com.perapps.testingpractice.databinding.FragmentImagePickBinding
import com.perapps.testingpractice.other.Constants.GRID_SPAN_COUNT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_image_pick) {

    private var _binding: FragmentImagePickBinding? = null
    private val binding: FragmentImagePickBinding
        get() = _binding!!

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentImagePickBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        setUpRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}