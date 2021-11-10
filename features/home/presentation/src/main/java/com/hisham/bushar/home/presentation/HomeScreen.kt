package com.hisham.bushar.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.hisham.bushar.home.domain.states.MovieItemState

const val TAG_PROGRESS = "progress"

@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onClick: (MovieItemState) -> Unit,
) {
    val state = vm.moviesPagingFlow.collectAsLazyPagingItems()

    MainContent(state, onClick = onClick)
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.error_message),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )

            OutlinedButton(onClick = onRetry) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag(TAG_PROGRESS),
            color = MaterialTheme.colors.secondary
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainContent(
    lazyPagingItems: LazyPagingItems<MovieItemState>,
    onClick: (MovieItemState) -> Unit,
) {
    val scrollState = rememberLazyListState()
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        cells = GridCells.Adaptive(120.dp),
        state = scrollState,
        contentPadding = PaddingValues(2.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { movieItemState ->
                MovieCard(movie = movieItemState, onClick = onClick)
            }
        }

        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                item { LoadingContent() }
            }
            lazyPagingItems.loadState.append is LoadState.Loading -> {
                item { LoadingContent() }
            }
            lazyPagingItems.loadState.refresh is LoadState.Error -> {
                item { ErrorContent { lazyPagingItems.retry() } }
            }
            lazyPagingItems.loadState.append is LoadState.Error -> {
                item { ErrorContent { lazyPagingItems.retry() } }
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieItemState,
    onClick: (MovieItemState) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                .shadow(4.dp),
            shape = MaterialTheme.shapes.small,
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.background,
        ) {
            Column(
                modifier = Modifier
                    .clickable(onClick = { onClick(movie) })
                    .wrapContentSize()
            ) {
                val contentWidth = 100.dp
                val contentHeight = 140.dp

                Image(
                    painter = rememberImagePainter(
                        data = movie.coverUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    modifier = Modifier
                        .width(contentWidth)
                        .height(contentHeight),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = movie.name
                )

                Text(
                    text = movie.name,
                    modifier = Modifier
                        .width(contentWidth)
                        .padding(3.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}
