package co.feip.fefu2025.presentation.anime_favorites

import co.feip.fefu2025.domain.model.AnimePoster

data class AnimeFavoritesState (
    val posters: List<AnimePoster>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)