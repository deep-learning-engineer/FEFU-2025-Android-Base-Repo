package co.feip.fefu2025.domain.model

data class AnimeDetails (
    val title: String,
    val description: String?,
    val drawableName: String?,
    val genres: List<String>?,
    val releaseYear: Int?,
    val rating: Float?,
    val ratingInfo: Map<Int, Int>,
    val episodeCount: Int?,
    val recommendations: List<AnimePoster>,
    val id: Int
)