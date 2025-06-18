package co.feip.fefu2025.domain.model

data class AnimePoster(
    val title: String,
    val imageUrl: String?,
    val genres: List<String>?,
    val rating: Float?,
    val id: Int
)