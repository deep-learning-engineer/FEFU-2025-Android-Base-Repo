package co.feip.fefu2025.data.remoute.dto.anime_details

import kotlinx.serialization.Serializable

@Serializable
data class Aired(
    val from: String?,
    val prop: Prop?,
    val to: String?
)