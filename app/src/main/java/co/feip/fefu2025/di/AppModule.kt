package co.feip.fefu2025.di

import co.feip.fefu2025.data.remote.AnimeAPI
import co.feip.fefu2025.data.repository.RepositoryImpl
import co.feip.fefu2025.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAnimeRepository(api: AnimeAPI): AnimeRepository {
        return RepositoryImpl(api)
    }

    @Provides
    fun provideApi(): AnimeAPI{
        val api = object : AnimeAPI {}
        return api
    }

}