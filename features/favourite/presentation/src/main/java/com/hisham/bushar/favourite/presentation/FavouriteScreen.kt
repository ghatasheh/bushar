package com.hisham.bushar.favourite.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import com.hisham.bushar.common.compose.Layout
import com.hisham.bushar.common.compose.bodyWidth
import com.hisham.bushar.common.compose.itemsInGrid
import com.hisham.bushar.common.compose.rememberFlowWithLifecycle
import com.hisham.bushar.favourite.domain.state.Favourite

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouriteViewModel,
) {
    val state by rememberFlowWithLifecycle(viewModel.favouriteFlow)
        .collectAsState(initial = emptyList())

    when {
        state.isNotEmpty() -> {
            FavouriteList(
                modifier = modifier,
                data = state,
            )
        }
        state.isEmpty() -> {
            NoDataContent()
        }
    }
}

@Composable
private fun FavouriteList(
    modifier: Modifier = Modifier,
    data: List<Favourite>
) {
    val columns = Layout.columns
    val bodyMargin = Layout.bodyMargin
    val gutter = Layout.gutter

    LazyColumn(
        contentPadding = PaddingValues(),
        modifier = Modifier
            .bodyWidth()
            .fillMaxHeight(),
    ) {
        itemsInGrid(
            items = data,
            columns = columns / 2,
            contentPadding = PaddingValues(horizontal = bodyMargin, vertical = gutter),
            verticalItemPadding = gutter,
            horizontalItemPadding = gutter,
        ) { movie ->
            FavouriteItem(
                movie = movie,
                modifier = modifier
                    .aspectRatio(2 / 3f)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun FavouriteItem(
    modifier: Modifier = Modifier,
    movie: Favourite,
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = rememberImagePainter(movie.poster) {
                    crossfade(true)
                },
                contentDescription = movie.title,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun NoDataContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = com.hisham.bushar.favourite.presentation.R.string.no_favourite),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary
        )
    }
}
