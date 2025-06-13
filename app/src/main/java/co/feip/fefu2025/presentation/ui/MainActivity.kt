package co.feip.fefu2025.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.presentation.anime_details.AnimeDetailsScreen
import co.feip.fefu2025.presentation.anime_details.AnimeDetailsViewModel
import co.feip.fefu2025.presentation.anime_favorites.AnimeFavoritesViewModel
import co.feip.fefu2025.presentation.anime_favorites.AnimeFavoritesScreen
import co.feip.fefu2025.presentation.anime_list.AnimeListScreen
import co.feip.fefu2025.presentation.anime_list.AnimeListViewModel
import co.feip.fefu2025.presentation.anime_list.AnimeSearchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Navigation()
            }
        }
    }
}
sealed class Destination{
    @Serializable
    object AnimeListScreen: Destination()
    @Serializable
    data class AnimeDetailsScreen(val id: Int): Destination()
    @Serializable
    data class AnimeFavoritesScreen(val id: Int): Destination()
    @Serializable
    object AnimeSearchScreen: Destination()
}

@Composable
fun Navigation(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Destination.AnimeListScreen
    ) {
        composable<Destination.AnimeListScreen>{
            val viewModel: AnimeListViewModel = hiltViewModel()
            val state = viewModel.state.value
            AnimeListScreen(
                state = state,
                navigateToDetails = { id -> navController.navigate(Destination.AnimeDetailsScreen(id)) },
                navigateToFavorites = { id -> navController.navigate(Destination.AnimeFavoritesScreen(id))},
                navigateToSearch = { navController.navigate(Destination.AnimeSearchScreen) },
                currentUserId = 1, // Пока заглушка
                onEvent = viewModel::onEvent
            )
        }

        composable<Destination.AnimeDetailsScreen> {
            val viewModel: AnimeDetailsViewModel = hiltViewModel()
            val state = viewModel.state.value
            AnimeDetailsScreen(
                state = state,
                navigateToDetails = { id -> navController.navigate(Destination.AnimeDetailsScreen(id)) },
                onBackClick = { navController.popBackStack() },
                onRetry = { viewModel.loadAnimeDetails() },
            )
        }

        composable<Destination.AnimeFavoritesScreen> {
            val viewModel: AnimeFavoritesViewModel = hiltViewModel()
            val state = viewModel.state.value
            AnimeFavoritesScreen(
                state = state,
                navigateToDetails = { id -> navController.navigate(Destination.AnimeDetailsScreen(id)) },
                onBackClick = { navController.popBackStack() },
                onRetry = { viewModel.loadFavorites() }
            )
        }

        composable<Destination.AnimeSearchScreen> {
            val viewModel: AnimeListViewModel = hiltViewModel()
            val state = viewModel.state.value

            AnimeSearchScreen(
                state = state,
                onBackClick = { navController.popBackStack() },
                navigateToDetails = { id -> navController.navigate(Destination.AnimeDetailsScreen(id)) },
                onEvent = viewModel::onEvent
            )
        }
    }
}