package com.perapps.testingpractice.repositories

import androidx.lifecycle.LiveData
import com.perapps.testingpractice.data.local.ShoppingItem
import com.perapps.testingpractice.data.remote.responses.ImageResponse
import com.perapps.testingpractice.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}