package com.elewa.nytimes.modules.news.feeds.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elewa.nytimes.core.ui.theme.NYTimesTheme
import com.elewa.nytimes.modules.news.feeds.domain.entity.NewsEntity

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    item: NewsEntity,
    onNewsClick: () -> Unit = {},
) {
    val expanded = remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier

    ) {

        Column(
            modifier = Modifier.clickable { expanded.value = !expanded.value },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current

            val imageRequest = remember {
                ImageRequest.Builder(context)
                    .data(item.previewUrl)
                    .error(com.elewa.nytimes.R.drawable.img_placeholder)
                    .placeholder(com.elewa.nytimes.R.drawable.img_placeholder)
                    .crossfade(300)
                    .build()
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable { onNewsClick() },
                model = imageRequest,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
            )

            CardTitle(
                text = item.title?:""
            )

            if (!item.description.isNullOrEmpty()) {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = item.description,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = if (!expanded.value) 1 else Int.MAX_VALUE,
                        overflow = if (!expanded.value) TextOverflow.Ellipsis else TextOverflow.Visible
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview(modifier: Modifier = Modifier) {
    NYTimesTheme {
        NewsCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            NewsEntity(
                id = "1",
                previewUrl = "",
                title = "Anim id est laborum",
                description = "Viswash Kumar Ramesh was one of 242 people on the 787-8 Dreamliner that went down shortly after takeoff in Ahmedabad, India. Somehow, he walked away.",
                publishedDate = "2020"

                )
            )
    }
}