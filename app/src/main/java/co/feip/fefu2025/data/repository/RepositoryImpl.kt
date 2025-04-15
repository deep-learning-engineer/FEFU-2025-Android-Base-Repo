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
        return listOf(
            AnimePoster(
                "Атака Титанов",
                "attack_on_titan",
                listOf("Экшен", "Драма", "Фэнтези"),
                9.0f,
                1
            ),
            AnimePoster(
                "Наруто: Ураганные хроники",
                "naruto",
                listOf("Экшен", "Приключения", "Комедия"),
                8.7f,
                2
            ),
            AnimePoster(
                "One Piece",
                "onepiece",
                listOf("Экшен", "Приключения", "Комедия", "Фэнтези"),
                8.7f,
                3
            ),
            AnimePoster(
                "Магическая битва",
                "jujutsu_kaisen",
                listOf("Экшен", "Тёмное фэнтези", "Сверхъестественное"),
                8.8f,
                4
            ),
            AnimePoster(
                "Клинок, рассекающий демонов",
                "demon_slayer",
                listOf("Экшен", "Тёмное фэнтези", "Исторический"),
                8.9f,
                5
            )
        )
    }

    override suspend fun getAnimeDetailsById(id: Int): AnimeDetails {
        return AnimeDetails(
            title = "Блич: Тысячелетняя кровавая война",
            description = "Продолжение культового аниме, где Ичиго Куросаки и его друзья сталкиваются с Ванденрейхом, армией квинси, стремящейся уничтожить Общество Душ. Новые битвы, раскрытие тайн прошлого и эпическое завершение истории.",
            drawableName = "bleach",
            genres = listOf(
                "Экшен",
                "Приключения",
                "Сверхъестественное",
                "Сёнен",
                "Фэнтези",
                "Драма",
                "Суперсила"
            ),
            releaseYear = 2022,
            rating = 9.1f,
            ratingInfo = mapOf(
                5 to 30, 6 to 80, 7 to 250, 8 to 700, 9 to 1200, 10 to 900
            ),
            episodeCount = 26,
            recommendations = listOf(
                AnimePoster(
                    "Атака Титанов",
                    "attack_on_titan",
                    listOf("Экшен", "Драма", "Фэнтези"),
                    9.0f,
                    1
                ),
                AnimePoster(
                    "Наруто: Ураганные хроники",
                    "naruto",
                    listOf("Экшен", "Приключения", "Комедия"),
                    8.7f,
                    2
                ),
                AnimePoster(
                    "One Piece",
                    "onepiece",
                    listOf("Экшен", "Приключения", "Комедия", "Фэнтези"),
                    8.7f,
                    3
                ),
                AnimePoster(
                    "Магическая битва",
                    "jujutsu_kaisen",
                    listOf("Экшен", "Тёмное фэнтези", "Сверхъестественное"),
                    8.8f,
                    3
                ),
                AnimePoster(
                    "Клинок, рассекающий демонов",
                    "demon_slayer",
                    listOf("Экшен", "Тёмное фэнтези", "Исторический"),
                    8.9f,
                    4
                )
            ),
            id = 1
        )
    }

    override suspend fun getFavoriteAnimePostersByUserId(id: Int): List<AnimePoster> {
        return listOf(
            AnimePoster(
                "Атака Титанов",
                "attack_on_titan",
                listOf("Экшен", "Драма", "Фэнтези"),
                9.0f,
                1
            ),
            AnimePoster(
                "Наруто: Ураганные хроники",
                "naruto",
                listOf("Экшен", "Приключения", "Комедия"),
                8.7f,
                2
            ),
            AnimePoster(
                "One Piece",
                "onepiece",
                listOf("Экшен", "Приключения", "Комедия", "Фэнтези"),
                8.7f,
                3
            ),
            AnimePoster(
                "Наруто: Ураганные хроники",
                "naruto",
                listOf("Экшен", "Приключения", "Комедия"),
                8.7f,
                2
            ),
            AnimePoster(
                "One Piece",
                "onepiece",
                listOf("Экшен", "Приключения", "Комедия", "Фэнтези"),
                8.7f,
                3
            )
        )
    }
}