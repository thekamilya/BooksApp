package com.example.books.presentation.ui.screens.home.profileScreen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.presentation.ui.screens.home.common.BookDetailScreen
import com.example.books.presentation.ui.screens.home.common.BookSelectionViewModel
import com.example.books.presentation.ui.screens.home.discoverScreen.InnerScreens
import com.example.books.presentation.ui.screens.home.libraryScreen.LibraryListScreen

@Composable
fun ProfileNavScreen(onLogOut:()-> Unit) {
    val navController = rememberNavController()
    var bookSelectionViewModel = BookSelectionViewModel()

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
            ProfileScreen(navController, bookSelectionViewModel,onLogOut= onLogOut)
        }
        composable(route = InnerScreens.DETAIL.name) {
            BookDetailScreen(navController,bookSelectionViewModel.chosenBookId.value )
        }
    }
}