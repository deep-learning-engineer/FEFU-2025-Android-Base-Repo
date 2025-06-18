package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Studio(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)