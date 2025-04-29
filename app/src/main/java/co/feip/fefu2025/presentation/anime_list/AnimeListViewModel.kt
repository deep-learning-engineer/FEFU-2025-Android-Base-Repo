package co.feip.fefu2025.presentation.anime_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.use_case.get_posters.GetAnimePosterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getAnimePosterUseCase: GetAnimePosterUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AnimeListState())
    val state: State<AnimeListState> = _state
    private val _allPosters = mutableStateOf<List<AnimePoster>?>(null)

    init {
        loadAnimeList()
    }

    fun loadAnimeList() {
        _state.value = _state.value.copy(isLoading = true, error = "")

        getAnimePosterUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _allPosters.value = result.data

                    if (_state.value.searchQuery.isBlank()) {
                        _state.value = _state.value.copy(
                            posters = result.data,
                            isLoading = false
                        )
                    } else {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                    }
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun filterPostersByQuery(query: String) {
        val trimmedQuery = query.trim()
        val lowerQuery = trimmedQuery.lowercase()

        _state.value = _state.value.copy(
            searchQuery = trimmedQuery,
            isLoading = true
        )

        viewModelScope.launch {
            delay(300)
            val allPosters = _allPosters.value ?: emptyList()

            val filtered = if (lowerQuery.isBlank()) {
                emptyList()
            } else {
                allPosters.filter {
                    it.title.lowercase().contains(lowerQuery)
                }
            }

            _state.value = _state.value.copy(
                posters = filtered,
                isLoading = false
            )
        }
    }
}
