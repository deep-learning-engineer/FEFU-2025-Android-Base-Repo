package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Prop(
    val from: From,
    val string: String,
    val to: To
)