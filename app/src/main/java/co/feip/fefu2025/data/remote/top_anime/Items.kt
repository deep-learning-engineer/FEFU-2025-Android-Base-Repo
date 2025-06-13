package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Items(
    val count: Int,
    val per_page: Int,
    val total: Int
)