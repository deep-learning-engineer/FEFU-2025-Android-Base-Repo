package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)