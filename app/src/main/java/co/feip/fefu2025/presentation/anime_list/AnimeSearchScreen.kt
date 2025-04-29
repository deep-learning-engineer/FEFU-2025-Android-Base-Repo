package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.SimpleAnimeCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeSearchScreen(
    state: AnimeListState,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = onQueryChange,
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
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error.isNotBlank() -> {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(state.error)
                        Button(onClick = onRetry) { Text("Повторить") }
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
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
