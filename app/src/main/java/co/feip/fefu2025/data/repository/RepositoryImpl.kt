package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.mappers.toDomain
import co.feip.fefu2025.data.remote.AnimeApi
import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.model.AnimePoster
import co.feip.fefu2025.domain.repository.AnimeRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: AnimeApi
) : AnimeRepository {

    override suspend fun getAnimePosters(page: Int): List<AnimePoster> {
        return api.getTopAnime(page).data.map { animeDto ->
            animeDto.toDomain()
        }
    }

    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails {
        return api.getAnimeById(id).data.toDomain()
    }

    override suspend fun getRecomendationsAnime(): List<AnimePoster> {
        return api.getRecommendationsAnime().data.flatMap { entry ->
            entry.entry.map {
                it.toDomain()
            }
        }
    }

    override suspend fun searchAnime(query: String, page: Int): List<AnimePoster> {
        return api.searchAnime(query, page).data.map { animeDto ->
            animeDto.toDomain()
        }
    }
}