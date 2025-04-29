package co.feip.fefu2025.presentation.anime_details

import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import co.feip.fefu2025.SimpleAnimeCard
import co.feip.fefu2025.presentation.anime_details.components.CustomFlexBox
import co.feip.fefu2025.presentation.anime_details.components.RatingChart
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsScreen(
    state: AnimeDetailsState,
    navigateToDetails: (Int) -> Unit,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val density = LocalDensity.current

    val chipBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val chipTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    val horizontalSpacingPx = with(density) { 6.dp.toPx().toInt() }
    val verticalSpacingPx = with(density) { 6.dp.toPx().toInt() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when {
                            state.isLoading -> "Загрузка..."
                            state.error.isNotBlank() -> "Ошибка"
                            else -> state.details?.title ?: "Детали аниме"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
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
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp),
                        strokeWidth = 6.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                state.error.isNotBlank() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = onRetry,
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Повторить")
                        }
                    }
                }

                state.details != null -> {
                    val anime = state.details
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        val mainImageResourceId = if (!anime.drawableName.isNullOrBlank()) {
                            context.resources.getIdentifier(
                                anime.drawableName,
                                "drawable",
                                context.packageName
                            )
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
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Постер недоступен",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
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
                                Text(
                                    text = "Описание отсутствует.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        val recommendations = anime.recommendations
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
                                            animePoster = recommendedAnime,
                                            navigateToDetails = { navigateToDetails(recommendedAnime.id) }
                                        )
                                    }
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}