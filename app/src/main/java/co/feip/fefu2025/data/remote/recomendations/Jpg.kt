package co.feip.fefu2025.data.remoute.dto.recomendations
import kotlinx.serialization.Serializable

@Serializable
data class Jpg(
    val image_url: String,
    val large_image_url: String,
    val small_image_url: String
)