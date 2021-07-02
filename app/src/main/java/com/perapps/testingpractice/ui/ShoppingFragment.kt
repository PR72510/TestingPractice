package com.perapps.testingpractice.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.perapps.testingpractice.R
import com.perapps.testingpractice.adapter.ShoppingAdapter
import com.perapps.testingpractice.databinding.FragmentShoppingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingFragment @Inject constructor(
    private val shoppingAdapter: ShoppingAdapter,
    var viewModel: ShoppingViewModel? = null
) : Fragment(R.layout.fragment_shopping) {

    private var _binding: FragmentShoppingBinding? = null
    private val binding: FragmentShoppingBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        _binding = FragmentShoppingBinding.bind(view)
        setRecyclerView()
        setListeners()
        handleObservers()
    }

    val swipeItemCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = shoppingAdapter.shoppingItems[viewHolder.layoutPosition]
            viewModel?.deleteShoppingItem(item)

            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo"){
                    viewModel?.insertShoppingItemToDb(item)
                }
            }.show()
        }

    }

    private fun setRecyclerView() {
        binding.rvShoppingItems.apply {
            adapter = shoppingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(swipeItemCallback).attachToRecyclerView(this)
        }
    }

    private fun handleObservers() {
        viewModel?.shoppingItems?.observe(viewLifecycleOwner, {
            shoppingAdapter.shoppingItems = it
        })
        viewModel?.totalPrice?.observe(viewLifecycleOwner, {
            val price = it ?: 0f
            val priceText = "Total Price: $price"
            binding.tvShoppingItemPrice.text = priceText
        })
    }

    private fun setListeners() {
        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}