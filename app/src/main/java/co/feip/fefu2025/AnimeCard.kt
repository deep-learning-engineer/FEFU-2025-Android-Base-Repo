package co.feip.fefu2025

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.Locale

val SimpleAnimeCardHeight: Dp = 250.dp

@Composable
fun SimpleAnimeCard(
    title: String,
    drawableName: String?,
    genres: List<String>?,
    rating: Float?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val resources: Resources = context.resources
    val packageName: String = context.packageName

    val resourceId = if (drawableName != null && drawableName.isNotBlank()) {
        resources.getIdentifier(drawableName, "drawable", packageName)
    } else {
        0
    }

    Card(
        modifier = modifier
            .width(130.dp)
            .height(SimpleAnimeCardHeight)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            if (resourceId != 0) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = title,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("?", style = MaterialTheme.typography.displaySmall, color = Color.DarkGray)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .padding(top = 6.dp, bottom = 8.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!genres.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = genres.joinToString(", "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                val textRating = if (rating != null) {
                    "⭐ %.1f".format(Locale.US, rating)
                } else {
                    "⭐ Нет рейтинга"
                }

                Text(
                    text = textRating,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
    }
}

// --- Previews ---

// Preview с полным описанием
@Preview(showBackground = true, name = "Card with All Info")
@Composable
fun SimpleAnimeCardAllInfoPreview() {
    MaterialTheme {
        SimpleAnimeCard(
            title = "Блич: Тысячелетняя кровавая война - Часть Вторая",
            drawableName = "anime",
            genres = listOf("Экшен", "Приключения", "Фэнтези", "Драма"),
            rating = 9.1f
        )
    }
}

// Preview c длинным названием/жанрами для проверки обрезки
@Preview(showBackground = true, name = "Card with Long Content")
@Composable
fun SimpleAnimeCardLongContentPreview() {
    MaterialTheme {
        SimpleAnimeCard(
            title = "Атака Титанов: Финал – Заключительная глава (Часть 2)",
            drawableName = null,
            genres = listOf("Экшен", "Приключения", "Комедия", "Драма", "Фэнтези", "Ужасы", "Сверхъестественное", "Триллер"),
            rating = 9.0f
        )
    }
}

// Preview без жанров, рейтинга и изображения
@Preview(showBackground = true, name = "Card Minimal Info")
@Composable
fun SimpleAnimeCardMinimalPreview() {
    MaterialTheme {
        SimpleAnimeCard(
            title = "Наруто",
            drawableName = null,
            genres = null,
            rating = null
        )
    }
}

// Preview для сравнения высоты и расположения элементов внутри карточки
@Preview(showBackground = true, name = "Compare Layouts")
@Composable
fun CompareCardLayoutsPreview() {
    MaterialTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SimpleAnimeCard(
                title = "Очень Длинный Заголовок Для Теста В Две Строки",
                drawableName = "anime",
                genres = listOf("Экшен", "Фэнтези"),
                rating = 9.1f
            )

            SimpleAnimeCard(
                title = "Заголовок",
                drawableName = "anime",
                genres = listOf("Экшен", "Фэнтези"),
                rating = 8.7f
            )

            SimpleAnimeCard(
                title = "Еще Один Очень Длинный Заголовок Для Теста",
                drawableName = null,
                genres = null,
                rating = 8.0f
            )

            SimpleAnimeCard(
                title = "Просто Заголовок",
                drawableName = null,
                genres = null,
                rating = 7.5f
            )
        }
    }
}