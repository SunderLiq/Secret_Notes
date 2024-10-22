package app.compose.secretnotes.screens.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainScreen") {
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
    }
}