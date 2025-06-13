package co.feip.fefu2025.data.remoute.dto.recomendations
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val has_next_page: Boolean,
    val last_visible_page: Int
)