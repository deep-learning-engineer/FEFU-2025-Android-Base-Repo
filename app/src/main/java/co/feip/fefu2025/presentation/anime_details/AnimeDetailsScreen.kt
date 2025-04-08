package co.feip.fefu2025.presentation.anime_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.presentation.anime_details.components.RatingChart
import co.feip.fefu2025.presentation.anime_list.components.SimpleAnimeCard


@Composable
fun AnimeDetailScreen(
    anime: AnimePoster,           // Данные основного аниме
    ratings: Map<Int, Int>,     // Данные для графика рейтинга
    recommendations: List<AnimePoster> // Список рекомендованных аниме
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val mainImageResourceId = if (!anime.drawableName.isNullOrBlank()) {
            context.resources.getIdentifier(anime.drawableName, "drawable", context.packageName)
        } else {
            0
        }

        if (mainImageResourceId != 0) {
            Image(
                painter = painterResource(id = mainImageResourceId),
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                ,
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("Постер недоступен", color = Color.White)
            }
        }

        Column(Modifier.padding(16.dp)) {
            Text(
                text = anime.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (!anime.description.isNullOrBlank()) {
                Text(
                    text = anime.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    text = "Описание отсутствует.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        Column(Modifier.padding(horizontal = 16.dp)) {
            Text("Рейтинг пользователей", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            RatingChart(ratings = ratings)
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (recommendations.isNotEmpty()) {
            Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                Text(
                    text = "Может понравиться",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(recommendations.take(10)) { recommendedAnime ->
                        SimpleAnimeCard(
                            title = recommendedAnime.title,
                            description = recommendedAnime.description,
                            drawableName = recommendedAnime.drawableName,
                        )
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, heightDp = 1100)
@Composable
fun AnimeDetailScreenPreview() {
    val sampleAnimePoster = AnimePoster(
        title = "Блич",
        description = "Центральный персонаж «Блич» — пятнадцатилетний школьник Ичиго Куросаки, случайно получивший сверхъестественные силы синигами — богов смерти. Синигами в Японии представляют собой персонифицированную смерть, наподобие западного скелета с косой. Наделённый их способностями, Ичиго вынужден сражаться со злыми духами, защищать людей и отправлять души умерших в загробный мир.",
        drawableName = "bleach"
    )

    val sampleRatingsData = mapOf(
        1 to 10, 2 to 5, 3 to 20, 4 to 40, 5 to 80,
        6 to 150, 7 to 300, 8 to 500, 9 to 450, 10 to 350
    )

    val sampleRecommendationsData = listOf(
        AnimePoster("Блич", "Приключения Ичиго Куросаки, ставшего шинигами.", "bleach"),
        AnimePoster("Наруто", "История ниндзя Наруто Узумаки.", "naruto"),
        AnimePoster("One Piece", "Поиски величайшего сокровища пиратом Луффи.", "onepiece"),
        AnimePoster("Атака Титанов", "Человечество сражается с гигантами-людоедами.", "attack_on_titan"),
        AnimePoster("Блич", "Приключения Ичиго Куросаки, ставшего шинигами.", "bleach"),
        AnimePoster("Наруто", "История ниндзя Наруто Узумаки.", "naruto"),
        AnimePoster("One Piece", "Поиски величайшего сокровища пиратом Луффи.", "onepiece"),
        AnimePoster("Атака Титанов", "Человечество сражается с гигантами-людоедами.", "attack_on_titan"),
        AnimePoster("Блич", "Приключения Ичиго Куросаки, ставшего шинигами.", "bleach"),
        AnimePoster("Наруто", "История ниндзя Наруто Узумаки.", "naruto"),
    )

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimeDetailScreen(
                anime = sampleAnimePoster,
                ratings = sampleRatingsData,
                recommendations = sampleRecommendationsData
            )
        }
    }
}

// Preview для случая без описания или без картинки
@Preview(showBackground = true, name = "Details No Desc/Image")
@Composable
fun AnimeDetailScreenNoDataPreview() {
    val sampleAnimePoster = AnimePoster(
        title = "Аниме без данных",
        description = null,
        drawableName = null
    )
    val sampleRatingsData = mapOf(5 to 10, 6 to 20, 7 to 5)
    val sampleRecommendationsData = emptyList<AnimePoster>()

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimeDetailScreen(
                anime = sampleAnimePoster,
                ratings = sampleRatingsData,
                recommendations = sampleRecommendationsData
            )
        }
    }
}