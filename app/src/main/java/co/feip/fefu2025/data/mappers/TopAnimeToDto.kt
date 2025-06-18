package co.feip.fefu2025.data.mappers

import co.feip.fefu2025.data.remoute.dto.top_anime.Data
import co.feip.fefu2025.domain.model.AnimePoster

fun Data.toDomain() = AnimePoster(
    title = title,
    imageUrl = images.jpg.image_url,
    genres = genres.map { it.name },
    rating = score?.toFloat(),
    id = mal_id,
)

