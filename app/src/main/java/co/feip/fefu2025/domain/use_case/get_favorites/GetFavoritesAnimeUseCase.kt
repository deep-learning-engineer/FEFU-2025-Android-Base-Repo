package co.feip.fefu2025.domain.use_case.get_favorites

import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject


class GetFavoritesAnimeUseCase @Inject constructor (
    private val repository: AnimeRepository
) {
    operator fun invoke(userId: Int): Flow<Resource<List<AnimePoster>>> = flow {
        try {
            emit(Resource.Loading<List<AnimePoster>>())
            val favorites = repository.getFavoriteAnimePostersByUserId(userId)
            emit(Resource.Success<List<AnimePoster>>(favorites))
        } catch(e: HttpException) {
            emit(Resource.Error<List<AnimePoster>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<List<AnimePoster>>("Couldn't reach server. Check your internet connection."))
        }
    }
}