package com.perapps.testingpractice.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by PR72510 on 28/06/21.
 */

@Entity(tableName = "shopping_item")
data class ShoppingItem(
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
