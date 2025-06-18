package co.feip.fefu2025.data.remote

import co.feip.fefu2025.data.remoute.dto.anime_details.AnimeDetailsDto
import co.feip.fefu2025.data.remoute.dto.recomendations.RecomendationsDto
import co.feip.fefu2025.data.remoute.dto.top_anime.TopAnimeDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): TopAnimeDto

    @GET("recommendations/anime")
    suspend fun getRecommendationsAnime(): RecomendationsDto

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id")id: Int): AnimeDetailsDto

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String, @Query("page") page: Int): TopAnimeDto
}