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
            AnimeData("Блич", "Приключения Ичиго Куросаки, ставшего шинигами.", "bleach"),
            AnimeData("Наруто", "История ниндзя Наруто Узумаки.", "naruto"),
            AnimeData("One Piece", "Поиски величайшего сокровища пиратом Луффи.", "onepiece"),
            AnimeData("Атака Титанов", "Человечество сражается с гигантами-людоедами.", "attack_on_titan"),
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
                    description = randomAnime.description,
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