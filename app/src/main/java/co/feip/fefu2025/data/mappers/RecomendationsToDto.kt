package co.feip.fefu2025.data.mappers

import co.feip.fefu2025.data.remoute.dto.recomendations.Entry
import co.feip.fefu2025.domain.model.AnimePoster

fun Entry.toDomain() = AnimePoster(
    title = title,
    imageUrl = images.jpg.image_url,
    genres = null,
    rating = null,
    id = mal_id,
)

