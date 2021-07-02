package com.perapps.testingpractice.repositories

import androidx.lifecycle.LiveData
import com.perapps.testingpractice.data.local.ShoppingDao
import com.perapps.testingpractice.data.local.ShoppingItem
import com.perapps.testingpractice.data.remote.PixabayAPI
import com.perapps.testingpractice.data.remote.responses.ImageResponse
import com.perapps.testingpractice.other.Resource
import javax.inject.Inject

class DefaultShoppingRepositories @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Unknown Error Occurred", null)
            }else{
                Resource.error("Unknown Error Occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Cannot reach server", null)
        }
    }


}