package co.feip.fefu2025.data.mappers

import co.feip.fefu2025.data.remoute.dto.anime_details.Data
import co.feip.fefu2025.data.remoute.dto.recomendations.RecomendationsDto
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.model.AnimePoster

fun Data.toDomain() = AnimeDetails(
    title = title ?: "Unknown Title",
    description = synopsis,
    imageUrl = images?.jpg?.image_url,
    genres = genres.mapNotNull { it?.name },
    releaseYear = year,
    rating = score?.toFloat(),
    ratingInfo = emptyMap(),
    episodeCount = episodes,
    id = mal_id,
)

