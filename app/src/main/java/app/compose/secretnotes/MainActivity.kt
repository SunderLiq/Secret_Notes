package app.compose.secretnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.compose.secretnotes.screens.main.MainScreen
import app.compose.secretnotes.ui.theme.SecretNotesTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val fb = Firebase.firestore
            fb.collection("Notes").document().set(mapOf("First note" to "text text text"))
            SecretNotesTheme {
                MainScreen()
            }
        }
    }
}