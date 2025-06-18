package co.feip.fefu2025.presentation.anime_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.use_case.GetAnimeDetailsUseCase
import co.feip.fefu2025.domain.use_case.GetSimilarAnimeUseCase
import co.feip.fefu2025.presentation.ui.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getSimilarAnimeUseCase: GetSimilarAnimeUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(AnimeDetailsState())
    val state: State<AnimeDetailsState> = _state
    val animeId = savedStateHandle.toRoute<Destination.AnimeDetailsScreen>().id

    init {
        loadAnimeDetails()
        getSimilar()
    }

    fun loadAnimeDetails() {
        getAnimeDetailsUseCase(animeId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        details = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                    Log.e("AnimeDetailsViewModel", "Error fetching anime details: ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                        error = "",
                        details = null
                    )
                }
            }
        }.flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }

    private fun getSimilar(){
        getSimilarAnimeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        recommendations = result.data ?: emptyList(),
                        isLoadingRecommendations = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoadingRecommendations = false
                    )
                    Log.e("AnimeDetailsViewModel", "Error fetching similar anime: ${result.message}")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoadingRecommendations = true)
                }
            }
        }.flowOn(Dispatchers.Default).launchIn(viewModelScope)
    }

}