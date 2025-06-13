package co.feip.fefu2025.data.remoute.dto.anime_details
import kotlinx.serialization.Serializable

@Serializable
data class Title(
    val title: String?,
    val type: String?
)