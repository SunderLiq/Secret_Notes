package app.compose.secretnotes.screens.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.compose.secretnotes.login.SignInScreen
import app.compose.secretnotes.login.SignUpScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val main: String
    if (auth.currentUser?.email != null) {
        main = "mainScreen"
    }
    else {
        main = "SignUpScreen"
    }

    NavHost(navController = navController, startDestination = main) {
        composable("mainScreen") {
            noteId = 0
            HomeScreen(navController = navController)
        }
        composable("addNoteScreen") {
            AddNote(navController = navController)
        }
        composable("editNoteScreen") {
            EditNote(navController = navController)
        }
        composable("SignUpScreen") {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            SignUpScreen(navController = navController)
        }
        composable("SignInScreen") {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            SignInScreen(navController = navController)
        }
        composable("LogOutScreen") {
            LogOut(navController = navController)
        }
    }
}