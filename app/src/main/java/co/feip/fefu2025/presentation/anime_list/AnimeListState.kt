package co.feip.fefu2025.presentation.anime_list

import co.feip.fefu2025.domain.model.AnimePoster

data class AnimeListState(
    val posters: List<AnimePoster>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
