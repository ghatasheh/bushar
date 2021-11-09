package com.app.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val TAG_PROGRESS = "progress"

@Composable
fun HomeScreen(
    vm: HomeViewModel,
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
            Column {
                Button(onClick = {}) {
                    Text("Test")
                }
            }
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
