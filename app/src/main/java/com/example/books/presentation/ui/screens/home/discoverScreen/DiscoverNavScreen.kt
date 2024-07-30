package com.example.books.presentation.ui.screens.home.discoverScreen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.presentation.ui.screens.home.common.BookDetailScreen
import com.example.books.presentation.ui.screens.home.common.BookSelectionViewModel

enum class InnerScreens {
    LIST,
    DETAIL,
}

@Composable
fun DiscoverNavScreen(bookSelectionViewModel: BookSelectionViewModel = BookSelectionViewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = InnerScreens.LIST.name,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
    ) {
        composable(route = InnerScreens.LIST.name) {
            BookListScreen(navController, bookSelectionViewModel)
        }
        composable(route = InnerScreens.DETAIL.name) {
            BookDetailScreen(navController,bookSelectionViewModel.chosenBookId.value )
        }
    }
}

