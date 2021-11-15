package com.hisham.bushar.home.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.hisham.bushar.common.compose.rememberFlowWithLifecycle
import com.hisham.bushar.design.widgets.ErrorContent
import com.hisham.bushar.design.widgets.LoadingContent
import com.hisham.bushar.home.domain.states.MovieItemState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val state = rememberFlowWithLifecycle(viewModel.moviesPagingFlow)
        .collectAsLazyPagingItems()

    MainContent(
        lazyPagingItems = state,
        onClick = { viewModel.handleAction(HomeAction.OnMovieClicked(it)) },
        onFavouriteClick = { viewModel.handleAction(HomeAction.OnFavouriteClicked(it)) },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainContent(
    lazyPagingItems: LazyPagingItems<MovieItemState>,
    onClick: (MovieItemState) -> Unit,
    onFavouriteClick: (MovieItemState) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()

    val isListEmpty = lazyPagingItems.loadState.refresh is LoadState.NotLoading && lazyPagingItems.itemCount == 0
    val isLoading = lazyPagingItems.loadState.source.refresh is LoadState.Loading
    val isError = lazyPagingItems.loadState.source.refresh is LoadState.Error
    val showGrid = !isListEmpty

    // error from mediator or paging
    val errorState = lazyPagingItems.loadState.source.append as? LoadState.Error
        ?: lazyPagingItems.loadState.source.prepend as? LoadState.Error
        ?: lazyPagingItems.loadState.append as? LoadState.Error
        ?: lazyPagingItems.loadState.prepend as? LoadState.Error
    when {
        isListEmpty -> {
            LoadingContent()
        }
        isLoading -> {
            LoadingContent()
        }
        isError -> {
            ErrorContent {
                lazyPagingItems.retry()
            }
        }
        showGrid -> {
            GridLayout(
                scrollState,
                lazyListState,
                lazyPagingItems,
                onClick,
                onFavouriteClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GridLayout(
    scrollState: ScrollState,
    lazyListState: LazyListState,
    lazyPagingItems: LazyPagingItems<MovieItemState>,
    onClick: (MovieItemState) -> Unit,
    onFavouriteClick: (MovieItemState) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .scrollable(state = scrollState, orientation = Orientation.Vertical),
        cells = GridCells.Adaptive(120.dp),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
    ) {
        items(lazyPagingItems.itemCount) { index ->
            lazyPagingItems[index]?.let { movieItemState ->
                MovieCard(
                    movie = movieItemState,
                    onClick = onClick,
                    onFavouriteClick = onFavouriteClick
                )
            }
        }

        when {
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
    onClick: (MovieItemState) -> Unit,
    onFavouriteClick: (MovieItemState) -> Unit,
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

                Box(
                    modifier = Modifier
                        .width(contentWidth)
                        .height(contentHeight)
                ) {
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

                    var checked by remember { mutableStateOf(movie.isFavourite) }
                    IconToggleButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        checked = checked,
                        onCheckedChange = {
                            movie.isFavourite = it
                            onFavouriteClick(movie)
                            checked = it
                        }
                    ) {
                        val tint by animateColorAsState(if (checked) Color(0xFFEC407A) else Color(0xFFB0BEC5))
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(id = R.string.add_to_favourite),
                            tint = tint
                        )
                    }
                }

                Text(
                    text = movie.name,
                    modifier = Modifier
                        .width(contentWidth)
                        .padding(2.dp),
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
