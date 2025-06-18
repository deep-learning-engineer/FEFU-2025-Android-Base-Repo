package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeSearchScreen(
    state: AnimeListState,
    onEvent: (AnimeListEvent) -> Unit,
    onBackClick: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    val listState = rememberLazyGridState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { onEvent(AnimeListEvent.OnSearchQueryChange(it)) },
                        placeholder = { Text("Поиск...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Поиск") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
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
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error.isNotBlank() && state.posters.isNullOrEmpty() -> {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(state.error)
                        Button(onClick = { onEvent(AnimeListEvent.OnRetry) }) { Text("Повторить") }
                    }
                }
                state.searchQuery.isNotBlank() && state.posters.isNullOrEmpty() -> {
                    Text(
                        "Ничего не найдено",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = listState,
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        state.posters?.let { posters ->
                            items(posters.size) { index ->
                                val anime = posters[index]
                                SimpleAnimeCard(
                                    animePoster = anime,
                                    navigateToDetails = { navigateToDetails(anime.id) },
                                    modifier = Modifier.fillMaxWidth(),
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
                        }
                    }

                    LaunchedEffect(listState) {
                        snapshotFlow {
                            state.posters != null &&
                                    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index!! >= state.posters.size - 4
                        }
                            .distinctUntilChanged()
                            .collect { shouldLoad ->
                                if (shouldLoad && !state.isLoading) {
                                    onEvent(AnimeListEvent.OnSearchNextPage)
                                }
                            }
                    }
                }
            }
        }
    }
}
