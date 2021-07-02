package com.perapps.testingpractice.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.perapps.testingpractice.R
import com.perapps.testingpractice.data.local.ShoppingDao
import com.perapps.testingpractice.data.local.ShoppingDatabase
import com.perapps.testingpractice.data.remote.PixabayAPI
import com.perapps.testingpractice.other.Constants.BASE_URL
import com.perapps.testingpractice.other.Constants.DATABASE_NAME
import com.perapps.testingpractice.repositories.DefaultShoppingRepositories
import com.perapps.testingpractice.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepositories(dao, api) as ShoppingRepository

    @Provides
    @Singleton
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Provides
    @Singleton
    fun providesDao(
        database: ShoppingDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(PixabayAPI::class.java)
    }

}