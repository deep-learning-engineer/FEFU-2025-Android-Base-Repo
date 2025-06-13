package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class Broadcast(
    val day: String,
    val string: String,
    val time: String,
    val timezone: String
)