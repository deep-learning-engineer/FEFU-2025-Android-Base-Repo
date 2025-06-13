package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Licensor(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)