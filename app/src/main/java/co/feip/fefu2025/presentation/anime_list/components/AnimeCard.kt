package co.feip.fefu2025

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.domain.model.AnimePoster
import coil3.compose.AsyncImage
import java.util.Locale

val SimpleAnimeCardHeight: Dp = 250.dp

@Composable
fun SimpleAnimeCard(
    animePoster: AnimePoster,
    modifier: Modifier = Modifier,
    navigateToDetails: (Int) -> Unit,
    imageUrl: String? = null
) {
    val context = LocalContext.current
    val resources: Resources = context.resources
    val packageName: String = context.packageName

    val resourceId =
        if (animePoster.imageUrl != null && animePoster.imageUrl.isNotBlank()) {
            resources.getIdentifier(animePoster.imageUrl, "drawable", packageName)
        } else {
            0
        }

    Card(
        modifier = modifier
            .width(130.dp)
            .height(SimpleAnimeCardHeight)
            .clickable {
                navigateToDetails(animePoster.id)
            }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = animePoster.title,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } ?: Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("?", style = MaterialTheme.typography.displaySmall, color = Color.DarkGray)
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
                        text = animePoster.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (!animePoster.genres.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = animePoster.genres.joinToString(", "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                val textRating = if (animePoster.rating != null) {
                    "⭐ %.1f".format(Locale.US, animePoster.rating)
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

//// --- Previews ---
//
//@Preview(showBackground = true, name = "Card with All Info")
//@Composable
//fun SimpleAnimeCardAllInfoPreview() {
//    val posterData = AnimePoster(
//        title = "Блич: Тысячелетняя кровавая война - Часть Вторая",
//        drawableName = "bleach",
//        genres = listOf("Экшен", "Приключения", "Фэнтези", "Драма"),
//        rating = 9.1f
//    )
//    MaterialTheme {
//        SimpleAnimeCard(animePoster = posterData)
//    }
//}
//
//@Preview(showBackground = true, name = "Card with Long Content")
//@Composable
//fun SimpleAnimeCardLongContentPreview() {
//    val posterData = AnimePoster(
//        title = "Атака Титанов: Финал – Заключительная глава (Часть 2)",
//        drawableName = null,
//        genres = listOf("Экшен", "Приключения", "Комедия", "Драма", "Фэнтези", "Ужасы", "Сверхъестественное", "Триллер"),
//        rating = 9.0f
//    )
//    MaterialTheme {
//        SimpleAnimeCard(animePoster = posterData)
//    }
//}

//@Preview(showBackground = true, name = "Card Minimal Info")
//@Composable
//fun SimpleAnimeCardMinimalPreview() {
//    val posterData = AnimePoster(
//        title = "Наруто",
//        drawableName = null,
//        genres = null,
//        rating = null
//    )
//    MaterialTheme {
//        SimpleAnimeCard(animePoster = posterData)
//    }
//}

//@Preview(showBackground = true, name = "Compare Layouts")
//@Composable
//fun CompareCardLayoutsPreview() {
//    MaterialTheme {
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//
//            SimpleAnimeCard(
//                animePoster = AnimePoster(
//                    title = "Очень Длинный Заголовок Для Теста В Две Строки",
//                    drawableName = "bleach",
//                    genres = listOf("Экшен", "Фэнтези"),
//                    rating = 9.1f
//                )
//            )
//
//            SimpleAnimeCard(
//                animePoster = AnimePoster(
//                    title = "Заголовок",
//                    drawableName = "bleach",
//                    genres = listOf("Экшен", "Фэнтези"),
//                    rating = 8.7f
//                )
//            )
//
//            SimpleAnimeCard(
//                animePoster = AnimePoster(
//                    title = "Еще Один Очень Длинный Заголовок Для Теста",
//                    drawableName = null,
//                    genres = null,
//                    rating = 8.0f
//                )
//            )
//
//            SimpleAnimeCard(
//                animePoster = AnimePoster(
//                    title = "Просто Заголовок",
//                    drawableName = null,
//                    genres = null,
//                    rating = 7.5f
//                )
//            )
//        }
//    }
//}