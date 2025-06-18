package co.feip.fefu2025.presentation.anime_details

import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.model.AnimePoster

data class AnimeDetailsState(
    val details: AnimeDetails? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val isLoadingRecommendations: Boolean = false,
    val recommendations: List<AnimePoster> = emptyList(),
)
