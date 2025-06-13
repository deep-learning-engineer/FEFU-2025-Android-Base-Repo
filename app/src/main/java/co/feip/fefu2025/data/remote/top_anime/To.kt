package co.feip.fefu2025.data.remoute.dto.top_anime
import kotlinx.serialization.Serializable

@Serializable
data class To(
    val day: Int,
    val month: Int,
    val year: Int
)