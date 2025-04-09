package co.feip.fefu2025.domain.use_case.get_posters

import co.feip.fefu2025.common.Resource
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject


class GetAnimeDetailsUseCase @Inject constructor (
    private val repository: AnimeRepository
) {
    operator fun invoke(animeId: String): Flow<Resource<AnimeDetails>> = flow {
        try {
            emit(Resource.Loading<AnimeDetails>())
            val detail = repository.getAnimeDetailsById(animeId)
            emit(Resource.Success<AnimeDetails>(detail))
        } catch(e: HttpException) {
            emit(Resource.Error<AnimeDetails>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error<AnimeDetails>("Couldn't reach server. Check your internet connection."))
        }
    }
}