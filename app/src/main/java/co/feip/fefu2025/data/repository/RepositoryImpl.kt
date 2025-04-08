package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.remote.AnimeAPI
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.repository.AnimeRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: AnimeAPI
) : AnimeRepository {
    override suspend fun getAnimePosters(): List<AnimePoster> {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeDetailsById(id: String): AnimeDetails {
        TODO("Not yet implemented")
    }

}