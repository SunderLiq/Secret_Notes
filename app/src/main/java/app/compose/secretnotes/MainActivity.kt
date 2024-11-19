package app.compose.secretnotes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.compose.secretnotes.screens.main.MainScreen
import app.compose.secretnotes.ui.theme.SecretNotesTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecretNotesTheme {
                MainScreen()
            }
        }
    }
}

