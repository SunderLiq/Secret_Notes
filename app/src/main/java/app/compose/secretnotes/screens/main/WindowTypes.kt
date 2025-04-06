package app.compose.secretnotes.screens.main

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.compose.secretnotes.dataclasses.PINData
import app.compose.secretnotes.dialog.DeleteAccountDialog
import app.compose.secretnotes.login.EnterPinScreen
import app.compose.secretnotes.login.LogOut
import app.compose.secretnotes.login.PINScreen
import app.compose.secretnotes.login.ResetScreen
import app.compose.secretnotes.login.SignInScreen
import app.compose.secretnotes.login.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val fb = Firebase.firestore
    var main: String = "mainScreen"
    main = if (auth.currentUser?.email != null) {
        if (isPined(auth, fb)) "EnterPinScreen"
        else "mainScreen"
    } else {
        "SignUpScreen"
    }

    NavHost(navController = navController, startDestination = main) {
        composable("mainScreen", enterTransition = {
            return@composable fadeIn(tween(1000))
        },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }) {
            noteId = 0
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            HomeScreen(navController = navController)
        }
        composable("addNoteScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }) {
            AddNote(navController = navController)
        }
        composable("editNoteScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700)
                )
            }) {
            EditNote(navController = navController)
        }
        composable("SignUpScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }) {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            SignUpScreen(navController = navController)
        }
        composable("SignInScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }) {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            SignInScreen(navController = navController)
        }
        composable("LogOutScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }) {
            LogOut(navController = navController)
        }
        composable("ConfirmDeleteScreen") {
            DeleteAccountDialog(navController = navController, auth)
        }
        composable("PINScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }) {
            PINScreen(navController)
        }
        composable("EnterPinScreen") {
            EnterPinScreen(navController)
        }
        composable("ResetScreen", enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }) {
            BackHandler(true) {
                Log.i("myLog", "Clicked back")
            }
            ResetScreen(navController = navController)
        }
    }
}


@Composable
fun isPined (auth: FirebaseAuth, fb: FirebaseFirestore): Boolean {
    var ok by remember { mutableStateOf(true) }
    try {
        fb.collection("Notes").document("usersPIN").collection(auth.currentUser?.uid.toString())
            .document("PIN_Hash").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.toObject(PINData::class.java)?.pin.isNullOrBlank()){
                        Log.d("myLog","NO PIN")
                        ok = false
                    }
                    else {
                        ok = true
                        Log.d("myLog","PIN")
                    }
                }
            }
    }
    catch (e: Exception) {
        ok = false
    }
    return ok
}