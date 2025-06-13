package co.feip.fefu2025.data.remoute.dto.recomendations

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val content: String,
    val entry: List<Entry>,
    val mal_id: String,
    val user: User
)