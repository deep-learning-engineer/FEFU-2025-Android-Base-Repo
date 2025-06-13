package co.feip.fefu2025.data.remoute.dto.top_anime

import kotlinx.serialization.Serializable

@Serializable
data class Aired(
    val from: String,
    val prop: Prop,
    val to: String
)