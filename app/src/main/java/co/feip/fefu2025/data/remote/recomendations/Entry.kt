package co.feip.fefu2025.data.remoute.dto.recomendations
import kotlinx.serialization.Serializable

@Serializable
data class Entry(
    val images: Images,
    val mal_id: Int,
    val title: String,
    val url: String
)