package co.feip.fefu2025.domain.use_case

import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnimeByQueryUseCase  @Inject constructor(
    private val repository: AnimeRepository
) {
    operator fun invoke(query: String, page: Int) : Flow<Resource<List<AnimePoster>>> = flow {
        try {
            emit(Resource.Loading())
            val recommendations = repository.searchAnime(query, page)
            emit(Resource.Success(recommendations))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}