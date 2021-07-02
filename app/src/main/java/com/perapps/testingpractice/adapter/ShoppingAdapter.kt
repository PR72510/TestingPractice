package com.perapps.testingpractice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.perapps.testingpractice.data.local.ShoppingItem
import com.perapps.testingpractice.databinding.ItemShoppingBinding
import javax.inject.Inject


class ShoppingAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingAdapter.ShoppingItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding =
            ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ShoppingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        holder.bind(shoppingItem)
    }

    override fun getItemCount() = shoppingItems.size

    inner class ShoppingItemViewHolder(private val binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShoppingItem) {
            binding.apply {
                glide.load(item.imageUrl).into(ivShoppingImage)

                tvName.text = item.name
                val amountText = "${item.amount}x"
                tvShoppingItemAmount.text = amountText
                val priceText = "${item.price}"
                tvShoppingItemPrice.text = priceText
            }
        }
    }
}