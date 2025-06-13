package co.feip.fefu2025.presentation.anime_favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.use_case.GetSimilarAnimeUseCase
import co.feip.fefu2025.presentation.ui.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeFavoritesViewModel @Inject constructor(
    private val getFavoritesAnimeUseCase: GetSimilarAnimeUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(AnimeFavoritesState())
    val state: State<AnimeFavoritesState> = _state

    private var userId: Int = -1

    init {
        userId = savedStateHandle.toRoute<Destination.AnimeFavoritesScreen>().id
        loadFavorites()
    }

    fun loadFavorites() {
        _state.value = _state.value.copy(
            isLoading = true,
            error = "",
            posters = null
        )

        getFavoritesAnimeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AnimeFavoritesState(
                        posters = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = AnimeFavoritesState(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = AnimeFavoritesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}