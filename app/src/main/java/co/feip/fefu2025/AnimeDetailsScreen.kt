package co.feip.fefu2025

import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.TextView
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Locale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun AnimeDetailsScreen(
    anime: AnimeData,
    recommendations: List<AnimeData>
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val density = LocalDensity.current

    val chipBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val chipTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    val horizontalSpacingPx = with(density) { 6.dp.toPx().toInt() }
    val verticalSpacingPx = with(density) { 6.dp.toPx().toInt() }

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
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            // Плейсхолдер, если изображения нет
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("Постер недоступен", style = MaterialTheme.typography.bodyLarge, color = Color.White)
            }
        }

        Column(Modifier.padding(16.dp)) {
            Text(
                text = anime.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (anime.releaseYear != null) {
                    Text(
                        text = "Год: ${anime.releaseYear}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (anime.episodeCount != null) {
                        Text(
                            text = " • ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (anime.episodeCount != null) {
                    Text(
                        text = "Эпизоды: ${anime.episodeCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (anime.releaseYear != null || anime.episodeCount != null) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (anime.rating != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Рейтинг: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "⭐ %.1f".format(Locale.US, anime.rating),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }


            if (!anime.genres.isNullOrEmpty()) {
                Text(
                    text = "Жанр:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { ctx ->
                        CustomFlexBox(ctx).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )

                            this.horizontalSpacing = horizontalSpacingPx
                            this.verticalSpacing = verticalSpacingPx
                        }
                    },
                    update = { customFlexBox ->
                        customFlexBox.removeAllViews()

                        val genres = anime.genres

                        genres.forEach { genreText ->
                            val textView = TextView(customFlexBox.context).apply {
                                text = genreText
                                setPadding(
                                    with(density) { 8.dp.toPx().toInt() },
                                    with(density) { 4.dp.toPx().toInt() },
                                    with(density) { 8.dp.toPx().toInt() },
                                    with(density) { 4.dp.toPx().toInt() }
                                )

                                background = GradientDrawable().apply {
                                    shape = GradientDrawable.RECTANGLE
                                    cornerRadius = with(density) { 16.dp.toPx() }

                                    setColor(
                                        android.graphics.Color.argb(
                                            (chipBackgroundColor.alpha * 255).toInt(),
                                            (chipBackgroundColor.red * 255).toInt(),
                                            (chipBackgroundColor.green * 255).toInt(),
                                            (chipBackgroundColor.blue * 255).toInt()
                                        )
                                    )
                                }

                                setTextColor(
                                    android.graphics.Color.argb(
                                        (chipTextColor.alpha * 255).toInt(),
                                        (chipTextColor.red * 255).toInt(),
                                        (chipTextColor.green * 255).toInt(),
                                        (chipTextColor.blue * 255).toInt()
                                    )
                                )

                                textSize = 11f
                                layoutParams = ViewGroup.MarginLayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                ).apply {
                                    setMargins(
                                        with(density) { 2.dp.toPx().toInt() },
                                        with(density) { 2.dp.toPx().toInt() },
                                        with(density) { 2.dp.toPx().toInt() },
                                        with(density) { 2.dp.toPx().toInt() }
                                    )
                                }
                            }
                            customFlexBox.addView(textView)
                        }

                        customFlexBox.requestLayout()
                        customFlexBox.invalidate()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (anime.ratingInfo.isNotEmpty()) {
                Text(
                    text = "Рейтинг по оценкам:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                RatingChart(
                    ratings = anime.ratingInfo,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Описание:",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (!anime.description.isNullOrBlank()) {
                Text(
                    text = anime.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                // Плейсхолдер, если описание отсутствует
                Text(
                    text = "Описание отсутствует.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (recommendations.isNotEmpty()) {
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Может понравиться",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(recommendations.take(10)) { recommendedAnime ->
                        SimpleAnimeCard(
                            title = recommendedAnime.title,
                            drawableName = recommendedAnime.drawableName,
                            genres = recommendedAnime.genres,
                            rating = recommendedAnime.rating
                        )
                    }
                }
            }
        } else {
            // Если рекомендаций нет, добавим небольшой отступ снизу
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


// --- Previews ---

// Preview аниме со всеми полями
@Preview(showBackground = true, heightDp = 1200)
@Composable
fun AnimeDetailsScreenPreview() {
    val sampleAnimeData = AnimeData(
        title = "Блич: Тысячелетняя кровавая война",
        description = "Продолжение культового аниме, где Ичиго Куросаки и его друзья сталкиваются с Ванденрейхом, армией квинси, стремящейся уничтожить Общество Душ. Новые битвы, раскрытие тайн прошлого и эпическое завершение истории.",
        drawableName = "bleach",
        genres = listOf("Экшен", "Приключения", "Сверхъестественное", "Сёнен", "Фэнтези", "Драма", "Суперсила"),
        releaseYear = 2022,
        rating = 9.1f,
        ratingInfo = mapOf(
            5 to 30, 6 to 80, 7 to 250, 8 to 700, 9 to 1200, 10 to 900
        ),
        episodeCount = 26
    )

    val sampleRecommendationsData = listOf(
        AnimeData("Атака Титанов", null, "attack_on_titan", listOf("Экшен", "Драма", "Фэнтези"), 2013, 9.0f, emptyMap(), 88),
        AnimeData("Наруто: Ураганные хроники", null, "naruto", listOf("Экшен", "Приключения", "Комедия"), 2007, 8.7f, emptyMap(), 500),
        AnimeData("One Piece", null, "onepiece", listOf("Экшен", "Приключения", "Комедия", "Фэнтези"), 1999, 8.7f, emptyMap(), 1000),
        AnimeData("Магическая битва", null, "jujutsu_kaisen", listOf("Экшен", "Тёмное фэнтези", "Сверхъестественное"), 2020, 8.8f, emptyMap(), 47),
        AnimeData("Клинок, рассекающий демонов", null, "demon_slayer", listOf("Экшен", "Тёмное фэнтези", "Исторический"), 2019, 8.9f, emptyMap(), 55)
    )

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimeDetailsScreen(
                anime = sampleAnimeData,
                recommendations = sampleRecommendationsData
            )
        }
    }
}

// Preview аниме без необязательных полей
@Preview(showBackground = true, name = "Details No Optional Data")
@Composable
fun AnimeDetailsScreenNoOptionalDataPreview() {
    val sampleAnimeData = AnimeData(
        title = "Аниме",
        description = null,  // Нет описания
        drawableName = null, // Нет картинки
        genres = null,       // Нет жанров
        releaseYear = null,  // Нет года
        rating = null,       // Нет рейтинга
        ratingInfo = emptyMap(),   // Нет информации о рейтинге
        episodeCount = null  // Нет кол-ва эпизодов
    )

    val sampleRecommendationsData = emptyList<AnimeData>()

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimeDetailsScreen(
                anime = sampleAnimeData,
                recommendations = sampleRecommendationsData
            )
        }
    }
}