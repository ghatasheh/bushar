package com.app.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.app.home.domain.entities.MoviesListItemState

const val TAG_PROGRESS = "progress"

@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onClick: (MoviesListItemState) -> Unit,
) {
    val state = vm.uiState.collectAsState().value
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag(TAG_PROGRESS),
                    color = MaterialTheme.colors.secondary
                )
            }
        }
        state.movies.isNotEmpty() -> {
            MainContent(state.movies, onClick = onClick)
        }
        state.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Something went wrong")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainContent(
    movies: List<MoviesListItemState>,
    onClick: (MoviesListItemState) -> Unit,
) {
    val scrollState = rememberLazyListState()
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        cells = GridCells.Fixed(3),
        state = scrollState,
        contentPadding = PaddingValues(2.dp)
    ) {
        items(movies.size) { index ->
            MovieCard(movies[index], onClick = { onClick(movies[index]) })
        }
    }
}

@Composable
fun MovieCard(
    movie: MoviesListItemState,
    onClick: (MoviesListItemState) -> Unit
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
                        }),
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
