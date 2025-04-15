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
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getAnimePosterUseCase: GetAnimePosterUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AnimeListState())
    val state: State<AnimeListState> = _state

    private val _allPosters = mutableStateOf<List<AnimePoster>?>(null)

    init {
        getAnimeList()
    }

    private fun getAnimeList() {
        getAnimePosterUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AnimeListState(posters = result.data)
                    _allPosters.value = result.data
                    filterPosters()
                }
                is Resource.Error -> {
                    _state.value = AnimeListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = AnimeListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onQueryChange(input: String){
        _state.value = _state.value.copy(
            searchQuery = input
        )
        filterPosters()
    }

    private fun filterPosters() {
        val query = _state.value.searchQuery.lowercase()
        _allPosters.value?.let { allPosters ->
            _state.value = _state.value.copy(
                posters = if (query.isEmpty()) {
                    allPosters
                } else {
                    allPosters.filter { it.title.lowercase().contains(query) }
                }
            )
        }
    }
}