package co.feip.fefu2025.presentation.anime_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
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
fun AnimeListScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val animeTemplates = remember {
        listOf(
            AnimePoster("Атака Титанов", "attack_on_titan", listOf("Экшен", "Драма", "Фэнтези"), 9.0f),
            AnimePoster("Наруто: Ураганные хроники", "naruto", listOf("Экшен", "Приключения", "Комедия"), 8.7f),
            AnimePoster("One Piece",  "onepiece", listOf("Экшен", "Приключения", "Комедия", "Фэнтези"), 8.7f),
            AnimePoster("Магическая битва", "jujutsu_kaisen", listOf("Экшен", "Тёмное фэнтези", "Сверхъестественное"), 8.8f),
            AnimePoster("Клинок, рассекающий демонов",  "demon_slayer", listOf("Экшен", "Тёмное фэнтези", "Исторический"), 8.9f)
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Аниме Каталог") },
                actions = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
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
            items(
                count = 30,
            ) { index ->

                val randomAnime = animeTemplates.random() // Получаем случайный AnimePoster из списка

                SimpleAnimeCard(
                    animePoster = randomAnime,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        AnimeListScreen()
    }
}