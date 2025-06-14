package com.elewa.nytimes.modules.news.feeds.presentation.viewmodel

import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity
import com.elewa.nytimes.modules.news.feeds.domain.interceptor.GetNewsListUseCase
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsIntent
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private lateinit var getNewsListUseCase: GetNewsListUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getNewsListUseCase = mockk()
        viewModel = NewsViewModel(getNewsListUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when LoadNews is called, it emits news list successfully`() = runTest {
        // Given
        val newsList = listOf(
            NewsEntity("1", "Title 1", "Desc 1", "img1", "2024-01-01"),
            NewsEntity("2", "Title 2", "Desc 2", "img2", "2024-01-02")
        )

        coEvery { getNewsListUseCase.execute(Unit) } returns flow {
            emit(newsList)
        }

        // When
        viewModel.onIntent(NewsIntent.LoadNews)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertEquals(newsList, state.news)
        assertNull(state.error)
    }

    @Test
    fun `when LoadNews fails, it sets error in state`() = runTest {
        // Given
        coEvery { getNewsListUseCase.execute(Unit) } returns flow {
            throw RuntimeException("Something went wrong")
        }

        // When
        viewModel.onIntent(NewsIntent.LoadNews)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(false, state.isLoading)
        assertTrue(state.news.isEmpty())
        assertEquals("Something went wrong", state.error)
    }

    @Test
    fun `when SelectNews is called, it updates selectedNews in state`() = runTest {
        val selectedNews = NewsEntity("3", "Title 3", "Desc 3", "img3", "2024-01-03")

        // When
        viewModel.onIntent(NewsIntent.SelectNews(selectedNews))

        // Then
        val state = viewModel.state.value
        assertEquals(selectedNews, state.selectedNews)
    }
}