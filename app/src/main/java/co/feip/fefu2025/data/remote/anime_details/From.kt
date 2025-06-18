package co.feip.fefu2025.data.remoute.dto.anime_details
import kotlinx.serialization.Serializable

@Serializable
data class From(
    val day: Int?,
    val month: Int?,
    val year: Int?
)