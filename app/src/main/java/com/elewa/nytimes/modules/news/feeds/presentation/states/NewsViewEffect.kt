package com.elewa.nytimes.modules.news.feeds.presentation.states

sealed class NewsViewEffect {
    data class ShowToast(val message: Int) : NewsViewEffect()
}