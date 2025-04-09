package co.feip.fefu2025

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

@Composable
fun RatingChart(
    ratings: Map<Int, Int>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
    maxBarHeight: Dp = 100.dp,
    barWidth: Dp = 20.dp
) {
    if (ratings.isEmpty()) {
        Text("Нет данных для отображения рейтинга", modifier = modifier)
        return
    }

    val maxCount = (ratings.values.maxOrNull() ?: 0) + 1
    val totalHeight = maxBarHeight + 70.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(totalHeight),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        (1..10).forEach { rating ->
            val count = ratings.getOrDefault(rating, 0)
            val barHeightRatio = max(0.01f, count.toFloat() / maxCount.toFloat())
            val barHeight = maxBarHeight * barHeightRatio

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .height(totalHeight)
                    .padding(bottom = 4.dp)
            ) {
                Spacer(Modifier.weight(1f))

                Text(
                    text = count.toString(),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                Box(
                    modifier = Modifier
                        .height(barHeight)
                        .width(barWidth)
                        .background(barColor)
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = rating.toString(),
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RatingChartPreview() {
    val sampleRatings = mapOf(
        1 to 100,
        2 to 50,
        3 to 200,
        4 to 150,
        5 to 300,
        6 to 400,
        7 to 600,
        8 to 800,
        9 to 750,
        10 to 500
    )
    MaterialTheme {
        RatingChart(ratings = sampleRatings, modifier = Modifier.padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun RatingChartEmptyPreview() {
    MaterialTheme {
        RatingChart(ratings = emptyMap(), modifier = Modifier.padding(16.dp))
    }
}