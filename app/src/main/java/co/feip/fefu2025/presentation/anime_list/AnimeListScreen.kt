package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.SimpleAnimeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    state: AnimeListState,
    navigateToDetails: (Int) -> Unit,
    navigateToFavorites: (Int) -> Unit,
    navigateToSearch: () -> Unit,
    currentUserId: Int,
    onRetry: () -> Unit
) {
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
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center).size(64.dp),
                        strokeWidth = 6.dp
                    )
                }
                state.error.isNotBlank() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.error, color = MaterialTheme.colorScheme.error)
                        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)) {
                            Text("Повторить")
                        }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        state.posters?.let { posters ->
                            items(posters.size) { index ->
                                val anime = posters[index]
                                SimpleAnimeCard(
                                    animePoster = anime,
                                    modifier = Modifier.fillMaxWidth(),
                                    navigateToDetails = { navigateToDetails(anime.id) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}