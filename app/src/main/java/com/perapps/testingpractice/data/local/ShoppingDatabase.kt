package com.perapps.testingpractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1, exportSchema = false
)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}