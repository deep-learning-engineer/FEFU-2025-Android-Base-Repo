package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.SimpleAnimeCard
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    state: AnimeListState,
    navigateToDetails: (Int) -> Unit,
    navigateToFavorites: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    currentUserId: Int,
    onEvent: (AnimeListEvent) -> Unit,
) {
    val listState = rememberLazyGridState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Аниме Каталог") },
                actions = {
                    IconButton(onClick = { navigateToFavorites(currentUserId) }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Понравившиеся аниме")
                    }
                    IconButton(onClick = navigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.posters.isNullOrEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp),
                        strokeWidth = 6.dp
                    )
                }

                state.error.isNotBlank() && state.posters.isNullOrEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.error, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { onEvent(AnimeListEvent.OnRetry) }, modifier = Modifier.padding(top = 16.dp)) {
                            Text("Повторить")
                        }
                    }
                }

                else -> {
                    state.posters?.let { posters ->
                        LaunchedEffect(listState, state.isLoading) {
                            snapshotFlow { listState.layoutInfo }
                                .map { layoutInfo ->
                                    layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                                }
                                .distinctUntilChanged()
                                .filter { lastVisibleIndex ->
                                    lastVisibleIndex >= posters.size - 1 && !state.isLoading
                                }
                                .collect {
                                    onEvent(AnimeListEvent.OnPageChange)
                                }
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            items(posters.size) { index ->
                                val anime = posters[index]
                                SimpleAnimeCard(
                                    animePoster = anime,
                                    modifier = Modifier.fillMaxWidth(),
                                    navigateToDetails = { navigateToDetails(anime.id) },
                                    imageUrl = anime.imageUrl
                                )
                            }
                            if (state.isLoading) {
                                item(span = { GridItemSpan(2) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                            if (state.error.isNotBlank() && !state.posters.isNullOrEmpty()) {
                                item(span = { GridItemSpan(2) }) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(state.error, color = MaterialTheme.colorScheme.error)
                                        Button(
                                            onClick = { onEvent(AnimeListEvent.OnRetry) },
                                            modifier = Modifier.padding(top = 8.dp)
                                        ) {
                                            Text("Повторить")
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}