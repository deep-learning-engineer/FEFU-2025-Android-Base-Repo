package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Theme(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)