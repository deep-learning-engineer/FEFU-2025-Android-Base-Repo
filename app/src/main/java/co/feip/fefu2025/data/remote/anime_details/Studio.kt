package co.feip.fefu2025.data.remoute.dto.anime_details
import kotlinx.serialization.Serializable

@Serializable
data class Studio(
    val mal_id: Int,
    val name: String?,
    val type: String?,
    val url: String?
)