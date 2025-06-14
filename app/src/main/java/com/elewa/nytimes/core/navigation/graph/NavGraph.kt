package com.elewa.nytimes.core.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.elewa.nytimes.core.navigation.News_GRAPH_ROUTE
import com.elewa.nytimes.core.navigation.ROOT_GRAPH_ROUTE

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = News_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE
    ) {
        marvelAppNav(navController)
    }
}