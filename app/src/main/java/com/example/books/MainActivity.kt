package com.example.books

import android.R
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.books.presentation.ui.screens.AuthScreen
import com.example.books.presentation.ui.screens.HomeScreen
import com.example.books.presentation.ui.theme.BooksTheme
import dagger.hilt.android.AndroidEntryPoint


enum class MainScreens {
    AUTH,
    HOME,
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksTheme {
                // A surface container using the 'background' color from the theme
                navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = MainScreens.AUTH.name,
                    enterTransition = {
                        EnterTransition.None
                    },
                    exitTransition = {
                        ExitTransition.None
                    },
                ) {
                    composable(route = MainScreens.AUTH.name) {
                        AuthScreen(navController)
                    }
                    composable(route = MainScreens.HOME.name) {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BooksTheme {
//        Greeting("Android")
//    }
//}