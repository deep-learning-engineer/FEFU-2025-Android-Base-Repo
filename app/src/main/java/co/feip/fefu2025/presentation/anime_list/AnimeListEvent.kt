package co.feip.fefu2025.presentation.anime_list

sealed interface AnimeListEvent {
    data object OnPageChange : AnimeListEvent
    data class OnSearchQueryChange(val query: String) : AnimeListEvent
    data object OnRetry : AnimeListEvent
    data object OnSearchNextPage : AnimeListEvent
}