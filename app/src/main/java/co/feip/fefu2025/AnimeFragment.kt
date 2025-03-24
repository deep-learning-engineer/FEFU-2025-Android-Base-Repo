package co.feip.fefu2025

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class AnimeFragment : Fragment() {

    private lateinit var animeImageView: ImageView
    private lateinit var animeTitleTextView: TextView
    private lateinit var genresContainer: CustomFlexBox
    private lateinit var animeDescriptionTextView: TextView
    private lateinit var animeRatingTextView: TextView
    private lateinit var animeYearTextView: TextView
    private lateinit var animeEpisodesTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.anime_screen, container, false)

        animeImageView = view.findViewById(R.id.anime_image)
        animeTitleTextView = view.findViewById(R.id.anime_title)
        genresContainer = view.findViewById(R.id.genres_container)
        animeDescriptionTextView = view.findViewById(R.id.anime_description)
        animeRatingTextView = view.findViewById(R.id.anime_rating)
        animeYearTextView = view.findViewById(R.id.anime_year)
        animeEpisodesTextView = view.findViewById(R.id.anime_episodes)

        populateAnimeData()

        return view
    }

    private fun populateAnimeData() {
        val animeTitle = "Блич"
        val animeImage = R.drawable.anime
        val genres = listOf("Файтинг", "Экшен")
        val description = "Лучший аниме-сериал жанар сёнен."
        val rating = "Рейтинг: 8.2"
        val year = "Год выпуска: 2004"
        val episodes = "Количество эпизодов: 396"

        animeTitleTextView.text = animeTitle
        animeImageView.setImageResource(animeImage)

        for (genre in genres) {
            val genreTextView = TextView(context).apply {
                text = genre
                setBackgroundResource(R.drawable.filter_background)
                setPadding(8, 4, 8, 4)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            genresContainer.addView(genreTextView)
        }

        animeDescriptionTextView.text = description
        animeRatingTextView.text = rating
        animeYearTextView.text = year
        animeEpisodesTextView.text = episodes
    }
}
