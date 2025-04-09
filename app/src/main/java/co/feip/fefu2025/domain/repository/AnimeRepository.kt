package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.AnimeDetails
import co.feip.fefu2025.domain.model.AnimePoster

interface AnimeRepository {
    suspend fun getAnimePosters(): List<AnimePoster>
    suspend fun getAnimeDetailsById(id: String): AnimeDetails
}