package co.feip.fefu2025

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val animeTemplates = remember {
        listOf(
            AnimeData("Атака Титанов", null, "attack_on_titan", listOf("Экшен", "Драма", "Фэнтези"), 2013, 9.0f, emptyMap(), 88),
            AnimeData("Наруто: Ураганные хроники", null, "naruto", listOf("Экшен", "Приключения", "Комедия"), 2007, 8.7f, emptyMap(), 500),
            AnimeData("One Piece", null, "onepiece", listOf("Экшен", "Приключения", "Комедия", "Фэнтези"), 1999, 8.7f, emptyMap(), 1000),
            AnimeData("Магическая битва", null, "jujutsu_kaisen", listOf("Экшен", "Тёмное фэнтези", "Сверхъестественное"), 2020, 8.8f, emptyMap(), 47),
            AnimeData("Клинок, рассекающий демонов", null, "demon_slayer", listOf("Экшен", "Тёмное фэнтези", "Исторический"), 2019, 8.9f, emptyMap(), 55)
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

                val randomAnime = animeTemplates.random() // Получаем случайный AnimeData из списка

                SimpleAnimeCard(
                    title = randomAnime.title,
                    genres = randomAnime.genres,
                    rating = randomAnime.rating,
                    drawableName = randomAnime.drawableName,
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
        MainScreen()
    }
}