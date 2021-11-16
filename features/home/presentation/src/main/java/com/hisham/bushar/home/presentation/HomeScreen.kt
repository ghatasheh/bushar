package com.hisham.bushar.home.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.hisham.bushar.common.compose.Layout
import com.hisham.bushar.common.compose.Placeholder
import com.hisham.bushar.common.compose.bodyWidth
import com.hisham.bushar.common.compose.itemsInGrid
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
    val isListEmpty =
        lazyPagingItems.loadState.refresh is LoadState.NotLoading && lazyPagingItems.itemCount == 0
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
            MoviesGrid(
                lazyPagingItems = lazyPagingItems,
                onClick = onClick,
                onFavouriteClick = onFavouriteClick,
            )
        }
    }
}

@Composable
fun MoviesGrid(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<MovieItemState>,
    onClick: (MovieItemState) -> Unit,
    onFavouriteClick: (MovieItemState) -> Unit,
) {
    val columns = Layout.columns
    val bodyMargin = Layout.bodyMargin
    val gutter = Layout.gutter

    LazyColumn(
        modifier = modifier
            .bodyWidth()
            .fillMaxHeight(),
    ) {
        itemsInGrid(
            lazyPagingItems = lazyPagingItems,
            columns = columns / 2,
            contentPadding = PaddingValues(horizontal = bodyMargin, vertical = gutter),
            verticalItemPadding = gutter,
            horizontalItemPadding = gutter,
        ) { movieItemState ->
            val mod = Modifier
                .aspectRatio(2 / 3f)
                .fillMaxWidth()
            if (movieItemState != null) {
                MovieCard(
                    movie = movieItemState,
                    onClick = onClick,
                    onFavouriteClick = onFavouriteClick,
                    modifier = mod,
                )
            } else {
                Placeholder(mod)
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                LoadingContent()
            }
        }
    }
}

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: MovieItemState,
    onClick: (MovieItemState) -> Unit,
    onFavouriteClick: (MovieItemState) -> Unit,
) {
    Card(
        modifier = modifier
            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
            .shadow(4.dp)
            .clickable { onClick(movie) },
        shape = MaterialTheme.shapes.small,
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Box(
            modifier = modifier
        ) {
            Image(
                painter = rememberImagePainter(
                    data = movie.coverUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
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
                val tint by animateColorAsState(
                    if (checked) Color(0xFFEC407A) else Color(
                        0xFFB0BEC5
                    )
                )
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(id = R.string.add_to_favourite),
                    tint = tint
                )
            }
        }
    }
}
