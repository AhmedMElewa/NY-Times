package com.elewa.nytimes.modules.news.feeds.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elewa.nytimes.R
import com.elewa.nytimes.core.expections.NetworkException
import com.elewa.nytimes.core.expections.ServerException
import com.elewa.nytimes.core.expections.UnknownException
import com.elewa.nytimes.modules.news.feeds.domain.interceptor.GetNewsListUseCase
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsIntent
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsViewEffect
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsListUseCase: GetNewsListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(NewsViewState())
    val state: StateFlow<NewsViewState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<NewsViewEffect>()
    val effect: SharedFlow<NewsViewEffect> = _effect.asSharedFlow()

    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.LoadNews -> loadNews()
            is NewsIntent.SelectNews -> _state.update {
                it.copy(selectedNews = intent.news)
            }
        }
    }

    private fun loadNews() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getNewsListUseCase.execute(Unit)

            result
                .catch { throwable ->
                    _state.update { it.copy(isLoading = false, error = throwable.message) }
                    throwable.handleError()
                }
                .collect {  newsList ->
                    _state.update { it.copy(isLoading = false, news = newsList) }

                }
        }
    }

    private fun updateError(@StringRes message: Int) {
        viewModelScope.launch {
            _effect.emit(NewsViewEffect.ShowToast(message))
        }
    }

    private fun Throwable.handleError() {
        when (this@handleError) {
            is NetworkException -> updateError(R.string.no_internet)
            is ServerException -> updateError(R.string.server_error)
            is UnknownException -> updateError(R.string.unknown_error)
            is IOException -> updateError(R.string.no_internet)
            else -> updateError(R.string.unknown_error)
        }
    }
}