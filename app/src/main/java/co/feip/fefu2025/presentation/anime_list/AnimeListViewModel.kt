package co.feip.fefu2025.presentation.anime_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.use_case.GetAnimeByQueryUseCase
import co.feip.fefu2025.domain.use_case.GetAnimePosterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getAnimePosterUseCase: GetAnimePosterUseCase,
    private val getAnimeByQueryUseCase: GetAnimeByQueryUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(AnimeListState())
    val state: State<AnimeListState> = _state
    private val _allPosters = mutableStateOf<List<AnimePoster>?>(null)

    private val _searchQuery = MutableStateFlow("")

    init {
        loadAnimeList(state.value.page)
        viewModelScope.launch {
            var previousPage = state.value.page
            snapshotFlow { state.value.page }
                .distinctUntilChanged()
                .collect { currentPage ->
                    if (currentPage > previousPage) {
                        loadAnimeList(currentPage)
                    }
                    previousPage = currentPage
                }
        }
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .onEach { query ->
                    val requestQuery = query.trim().lowercase()
                    if (requestQuery.isBlank()) {
                        if (_state.value.searchQuery.isNotBlank()) {
                            _state.value = _state.value.copy(
                                posters = _allPosters.value,
                                searchPage = 1
                            )
                        }
                        return@onEach
                    }
                    _state.value = _state.value.copy(searchPage = 1)
                    performSearch(requestQuery, 1)
                }
                .launchIn(viewModelScope)
        }
    }


    fun onEvent(event: AnimeListEvent) {
        when (event) {
            is AnimeListEvent.OnPageChange -> {
                _state.value = _state.value.copy(page = state.value.page + 1, isLoading = true)
            }
            is AnimeListEvent.OnSearchQueryChange -> {
                _state.value = _state.value.copy(searchQuery = event.query)
                _searchQuery.value = event.query
            }
            is AnimeListEvent.OnSearchNextPage -> {
                performSearch(state.value.searchQuery, state.value.searchPage + 1)
            }

            AnimeListEvent.OnRetry -> {
                if (state.value.searchQuery.isNotBlank()) {
                    performSearch(state.value.searchQuery, 1)
                } else {
                    loadAnimeList(_state.value.page)
                }
            }
        }
    }


    private fun loadAnimeList(page: Int) {
        _state.value = _state.value.copy(isLoading = true)
        getAnimePosterUseCase(page).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _allPosters.value = result.data

                    if (_state.value.searchQuery.isBlank()) {
                        val currentPosters: List<AnimePoster>? = _state.value.posters
                        _state.value = _state.value.copy(
                            posters = if (currentPosters != null) currentPosters + result.data!! else result.data!!,
                            isLoading = false
                        )
                    } else {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    Log.e("AnimeListViewModel", "Error loading anime posters: ${result.message}")
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }

    private fun performSearch(requestQuery: String, page: Int) {
        getAnimeByQueryUseCase(requestQuery, page)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val newPosters = result.data ?: emptyList()
                        val currentPosters = if (page == 1) emptyList() else _state.value.posters ?: emptyList()
                        _state.value = _state.value.copy(
                            posters = currentPosters + newPosters,
                            isLoading = false,
                            error = "",
                            searchPage = page
                        )
                    }
                    is Resource.Error -> {
                        Log.e("AnimeListViewModel", "Error searching anime: ${result.message}")
                        if (page > 1) {
                            _state.value = _state.value.copy(isLoading = false)
                        } else {
                            _state.value = _state.value.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }.flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }
}
