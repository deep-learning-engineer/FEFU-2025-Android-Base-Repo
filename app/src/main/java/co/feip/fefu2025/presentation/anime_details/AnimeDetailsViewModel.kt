package co.feip.fefu2025.presentation.anime_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.use_case.get_posters.GetAnimeDetailsUseCase
import co.feip.fefu2025.presentation.ui.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(AnimeDetailsState())
    val state: State<AnimeDetailsState> = _state

    private var animeId: Int = -1

    init {
        animeId = savedStateHandle.toRoute<Destination.AnimeDetailsScreen>().id
        loadAnimeDetails()
    }

    fun loadAnimeDetails() {
        _state.value = _state.value.copy(
            isLoading = true,
            error = "",
            details = null
        )

        getAnimeDetailsUseCase(animeId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AnimeDetailsState(
                        details = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = AnimeDetailsState(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = AnimeDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}