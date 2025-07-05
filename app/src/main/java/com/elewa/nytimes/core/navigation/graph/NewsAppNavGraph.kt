package com.elewa.nytimes.core.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.elewa.nytimes.core.navigation.News_GRAPH_ROUTE
import com.elewa.nytimes.core.navigation.Screen
import com.elewa.nytimes.modules.news.details.presentation.ui.NewsDetailsScreen
import com.elewa.nytimes.modules.news.feeds.presentation.ui.screen.NewsListScreen
import com.elewa.nytimes.modules.news.feeds.presentation.viewmodel.NewsViewModel


fun NavGraphBuilder.NyAppNav(navController: NavController) {
    navigation(
        startDestination = Screen.NewsList.route,
        route = News_GRAPH_ROUTE
    ) {
        composable(
            route = Screen.NewsList.route
        ) {
            val viewModel = it.sharedViewModel<NewsViewModel>(navController)
            NewsListScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.NewsDetails.route
        ) {
            val viewModel = it.sharedViewModel<NewsViewModel>(navController)
            NewsDetailsScreen(navController = navController, viewModel = viewModel)

        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}