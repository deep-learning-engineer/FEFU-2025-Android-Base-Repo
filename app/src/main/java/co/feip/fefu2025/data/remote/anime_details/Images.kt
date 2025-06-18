package co.feip.fefu2025.data.remoute.dto.anime_details
import kotlinx.serialization.Serializable

@Serializable
data class Images(
    val jpg: Jpg,
    val webp: Webp
)