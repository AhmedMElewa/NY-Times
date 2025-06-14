package com.elewa.nytimes.modules.news.details.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elewa.nytimes.R
import com.elewa.nytimes.core.navigation.Screen
import com.elewa.nytimes.core.ui.components.AppTopAppBar
import com.elewa.nytimes.core.ui.components.LoadingAnimation
import com.elewa.nytimes.modules.news.feeds.presentation.states.NewsIntent
import com.elewa.nytimes.modules.news.feeds.presentation.ui.components.NewsList
import com.elewa.nytimes.modules.news.feeds.presentation.viewmodel.NewsViewModel

@Composable
fun NewsDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewsViewModel,
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            AppTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Arrow Back Icon"
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

        when {
            state.isLoading -> LoadingAnimation(circleSize = 15.dp, spaceBetween = 5.dp)
            state.error != null -> Text(
                text = state.error ?: stringResource(R.string.error)
            )

            else -> Column(Modifier.padding(paddingValues)) {
                LazyColumn(Modifier.weight(1f)) {
                    item {
                        AsyncImage(
                            model =
                            ImageRequest.Builder(LocalContext.current)
                                .data("${state.selectedNews?.previewUrl}")
                                .crossfade(true)
                                .placeholder(R.drawable.img_placeholder)
                                .build(),
                            contentDescription = state.selectedNews?.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop

                        )
                        Text(
                            text = state.selectedNews?.publishedDate?:"",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal

                            ),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        )
                        Text(
                            text = state.selectedNews?.title?:"",
                            style = TextStyle(
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold

                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        )
                    }

                    state.selectedNews?.description?.let {
                        item {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = state.selectedNews?.description?:"",
                                color = MaterialTheme.colorScheme.onSurface,
                            )

                        }
                    }
                }
            }
        }

    }
}