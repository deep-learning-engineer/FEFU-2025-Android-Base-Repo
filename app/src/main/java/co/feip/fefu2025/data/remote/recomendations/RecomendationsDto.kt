package co.feip.fefu2025.data.remoute.dto.recomendations
import kotlinx.serialization.Serializable

@Serializable
data class RecomendationsDto(
    val `data`: List<Data>,
    val pagination: Pagination
)