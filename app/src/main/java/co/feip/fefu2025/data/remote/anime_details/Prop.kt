package co.feip.fefu2025.data.remoute.dto.anime_details
import kotlinx.serialization.Serializable

@Serializable
data class Prop(
    val from: From?,
    val string: String?,
    val to: To?
)