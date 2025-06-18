package co.feip.fefu2025.domain.use_case

import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject


class GetAnimePosterUseCase @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(page: Int): Flow<Resource<List<AnimePoster>>> = flow {
        try {
            emit(Resource.Loading())
            val poster = repository.getAnimePosters(page)
            emit(Resource.Success(poster))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}