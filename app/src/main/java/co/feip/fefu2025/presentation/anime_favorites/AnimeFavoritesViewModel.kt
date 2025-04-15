package co.feip.fefu2025.presentation.anime_favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.use_case.get_favorites.GetFavoritesAnimeUseCase
import co.feip.fefu2025.presentation.ui.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeFavoritesViewModel @Inject constructor(
    private val getFavoritesAnimeUseCase: GetFavoritesAnimeUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(AnimeFavoritesState())
    val state: State<AnimeFavoritesState> = _state

    init {
        val id = savedStateHandle.toRoute<Destination.AnimeFavoritesScreen>().id
        getFavoritesAnime(id)
    }

    private fun getFavoritesAnime(userId: Int) {
        getFavoritesAnimeUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AnimeFavoritesState(posters = result.data)
                }

                is Resource.Error -> {
                    _state.value = AnimeFavoritesState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = AnimeFavoritesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}