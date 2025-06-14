package com.elewa.nytimes.modules.news.feeds.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import com.elewa.nytimes.R
import com.elewa.nytimes.core.navigation.Screen
import com.elewa.nytimes.core.ui.components.AppTopAppBar
import com.elewa.nytimes.core.ui.components.LoadingAnimation
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsIntent
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsViewEffect
import com.elewa.nytimes.modules.news.feeds.presentation.ui.components.NewsList
import com.elewa.nytimes.modules.news.feeds.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewsViewModel,
) {


    val newsListState by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (newsListState.news.isEmpty()) {
            viewModel.onIntent(NewsIntent.LoadNews)
        }

        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is NewsViewEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            when {
                newsListState.isLoading -> LoadingAnimation(circleSize = 15.dp, spaceBetween = 5.dp)
                newsListState.error != null -> Text(
                    text = newsListState.error ?: stringResource(R.string.error)
                )

                else -> NewsList(
                    news = newsListState.news,
                    onNewsClick = {
                        viewModel.onIntent(NewsIntent.SelectNews(it))
                        navController.navigate(
                            route = Screen.NewsDetails.route
                        )
                    },
                )
            }


        }

    }

}

