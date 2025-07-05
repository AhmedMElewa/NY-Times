package com.elewa.nytimes.core.navigation

const val News_GRAPH_ROUTE = "News_LIST"

sealed class Screen(val route: String) {
    object NewsList : Screen(route = "news_list")
    object NewsDetails : Screen(route = "news_details")
}
