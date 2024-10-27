package app.compose.secretnotes.screens.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.compose.secretnotes.login.LogOut
import app.compose.secretnotes.login.SignInScreen
import app.compose.secretnotes.login.SignUpScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val main: String = if (auth.currentUser?.email != null) {
        "mainScreen"
    }
    else {
        "SignUpScreen"
    }

    NavHost(navController = navController, startDestination = main) {
        composable("mainScreen", enterTransition = {
            return@composable fadeIn(tween(1000))
        },
            popEnterTransition = {
                return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
            }) {
            noteId = 0
            HomeScreen(navController = navController)
        }
        composable("addNoteScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
            }) {
            AddNote(navController = navController)
        }
        composable("editNoteScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
            }) {
            EditNote(navController = navController)
        }
        composable("SignUpScreen", enterTransition = {
            return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
        },
            exitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
            }) {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            SignUpScreen(navController = navController)
        }
        composable("SignInScreen", enterTransition = {
            return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
        },
            exitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
            }) {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            SignInScreen(navController = navController)
        }
        composable("LogOutScreen", enterTransition = {
            return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700))
        },
            exitTransition = {
                return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))
            }) {
            LogOut(navController = navController)
        }
    }
}