package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.model.AnimePoster

interface AnimeRepository {
    suspend fun getAnimePosters(page: Int): List<AnimePoster>
    suspend fun getAnimeDetailsById(id: Int): AnimeDetails
    suspend fun getRecomendationsAnime(): List<AnimePoster>
    suspend fun searchAnime(query: String, page: Int): List<AnimePoster>
}