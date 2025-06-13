package co.feip.fefu2025.data.remoute.dto.recomendations
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val url: String,
    val username: String
)