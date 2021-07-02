package com.perapps.testingpractice.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.perapps.testingpractice.MainCoroutineRule
import com.perapps.testingpractice.getOrAwaitValueTest
import com.perapps.testingpractice.other.Constants
import com.perapps.testingpractice.other.Status
import com.perapps.testingpractice.repositories.FakeShoppingRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun insertItemWithEmptyFields_ReturnsError() {

        viewModel.insertShoppingItem("name", "", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertItemWithTooLongName_ReturnsError() {

        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.insertShoppingItem(string, "5", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertItemWithTooLongPrice_ReturnsError() {

        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.insertShoppingItem("name", "5", string)

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertItemWithInvalidAmount_ReturnsError() {

        viewModel.insertShoppingItem("name", "989898989898989899898", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun insertItemWithValidInput_ReturnsSuccess(){

        viewModel.insertShoppingItem("name", "5", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun afterItemInsertSuccessfully_ImageUrlEmpty(){
        viewModel.insertShoppingItem("name", "5", "3.0")

        val imageUrl = viewModel.curImageUrl.getOrAwaitValueTest()

        assertThat(imageUrl).isEmpty()
    }
}