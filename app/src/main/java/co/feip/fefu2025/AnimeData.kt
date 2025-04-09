package co.feip.fefu2025


data class AnimeData(
    val title: String,
    val description: String?,
    val drawableName: String?,
    val genres: List<String>?,
    val releaseYear: Int?,
    val rating: Float?,
    val ratingInfo: Map<Int, Int>,
    val episodeCount: Int?
)