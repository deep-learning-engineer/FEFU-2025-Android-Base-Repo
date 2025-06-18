package co.feip.fefu2025.domain.model

data class AnimeDetails (
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val genres: List<String>?,
    val releaseYear: Int?,
    val rating: Float?,
    val ratingInfo: Map<Int, Int>,
    val episodeCount: Int?,
    val id: Int
)