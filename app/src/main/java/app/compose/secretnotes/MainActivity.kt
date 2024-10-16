package app.compose.secretnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.compose.secretnotes.screens.main.Background
import app.compose.secretnotes.screens.main.Header
import app.compose.secretnotes.ui.theme.SecretNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecretNotesTheme {
                Background()
                Header()
            }
        }
    }
}