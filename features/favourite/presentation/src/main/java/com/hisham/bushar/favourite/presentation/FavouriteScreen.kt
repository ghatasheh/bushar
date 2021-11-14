package com.hisham.bushar.favourite.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hisham.bushar.favourite.domain.state.Favourite

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouriteViewModel,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val favouriteFlowLifecycleAware = remember(viewModel.favouriteFlow, lifecycleOwner) {
        viewModel.favouriteFlow.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val state = favouriteFlowLifecycleAware.collectAsState(initial = emptyList())

    when {
        state.value.isNotEmpty() -> {
            FavouriteList(
                modifier = modifier,
                data = state.value,
            )
        }
        state.value.isEmpty() -> {
            NoDataContent()
        }
    }
}

@Composable
private fun FavouriteList(
    modifier: Modifier,
    data: List<Favourite>) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(data.size) {
            Text(text = data[it].title)
            Spacer(modifier = Modifier.height(8.dp))
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
