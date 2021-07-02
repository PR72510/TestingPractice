package com.perapps.testingpractice.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.perapps.testingpractice.getOrAwaitValue
import com.perapps.testingpractice.launchFragmentInHiltContainer
import com.perapps.testingpractice.ui.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val item = ShoppingItem("apple", 12, 2f, "url", 1)
        dao.insertShoppingItem(item)

        val shoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(shoppingItems).contains(item)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val item = ShoppingItem("apple", 12, 2f, "url", 1)
        dao.insertShoppingItem(item)
        dao.deleteShoppingItem(item)

        val shoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(shoppingItems).doesNotContain(item)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val itemOne = ShoppingItem("apple", 12, 2f, "url", 1)
        val itemTwo = ShoppingItem("apple", 10, 1f, "url", 2)
        val itemThree = ShoppingItem("apple", 1, 50f, "url", 3)

        dao.insertShoppingItem(itemOne)
        dao.insertShoppingItem(itemTwo)
        dao.insertShoppingItem(itemThree)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(12 * 2f + 10 * 1f + 1 * 50f)
    }
}





