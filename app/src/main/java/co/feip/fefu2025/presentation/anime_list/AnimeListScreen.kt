package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.SimpleAnimeCard
import co.feip.fefu2025.domain.model.AnimePoster


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    state: AnimeListState,
    onQueryChange: (String) -> Unit,
    navigateToDetails: (Int) -> Unit,
    navigateToFavorites: (Int) -> Unit,
    currentUserId: Int
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Аниме Каталог") },
                actions = {
                    IconButton(onClick = { navigateToFavorites(currentUserId) }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Понравившиеся аниме"
                        )
                    }

                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { onQueryChange(it)},
                        placeholder = { Text("Поиск...") },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = "Поиск")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, start = 8.dp)
                            .height(50.dp),
                        singleLine = true
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
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
