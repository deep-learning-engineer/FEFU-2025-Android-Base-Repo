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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SimpleAnimeCard(
    title: String,
    description: String?,
    drawableName: String?,
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
            .height(IntrinsicSize.Min)
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
                        .weight(1f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("?", style = MaterialTheme.typography.displaySmall, color = Color.DarkGray)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 6.dp, bottom = 8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (!description.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


// Preview с описанием
@Preview(showBackground = true, name = "Card with Drawable")
@Composable
fun SimpleAnimeCardDrawablePreview() {
    MaterialTheme {
        SimpleAnimeCard(
            title = "Блич",
            description = "Аниме про рыжеволосого школьника Ичиго Куросаки, который случайно получает силы шинигами.",
            drawableName = "bleach"
        )
    }
}

// Preview для карточки без описания
@Preview(showBackground = true, name = "Card without Description")
@Composable
fun SimpleAnimeCardNoDescriptionPreview() {
    MaterialTheme {
        SimpleAnimeCard(
            title = "Ван Пис",
            description = null,
            drawableName = null
        )
    }
}

// Preview для карточки c длинным описанием
@Preview(showBackground = true, name = "Card with Long Description")
@Composable
fun SimpleAnimeCardLongDescriptionPreview() {
    MaterialTheme {
        SimpleAnimeCard(
            title = "Наруто: Ураганные хроники",
            description = "Продолжение истории Наруто Узумаки.",
            drawableName = null
        )
    }
}