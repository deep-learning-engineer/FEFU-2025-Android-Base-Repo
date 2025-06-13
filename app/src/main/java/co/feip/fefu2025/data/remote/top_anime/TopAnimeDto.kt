package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class TopAnimeDto(
    val `data`: List<Data>,
    val pagination: Pagination
)