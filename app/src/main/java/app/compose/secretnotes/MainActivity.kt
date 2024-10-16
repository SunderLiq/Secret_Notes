package app.compose.secretnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import app.compose.secretnotes.dataclasses.DataNote
import app.compose.secretnotes.screens.main.Background
import app.compose.secretnotes.screens.main.Header
import app.compose.secretnotes.screens.main.NotesList
import app.compose.secretnotes.ui.theme.NoteGreen
import app.compose.secretnotes.ui.theme.SecretNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecretNotesTheme {
                Background()
                Column {
                    Header()
                    NotesList(listOf(
                        DataNote(
                            "Первая заметка",
                            "Вот тут надо написать большой обширный текст" +
                                    " Ваще пипец какой большой текст, я даже не знаю" +
                                    " что тут написать. Ну просто пипец как много текста надо. " +
                                    "Прям переносись на вторую и третью строчку, давай да ура.",
                            "16.10.2024 21:08",
                            "",
                            NoteGreen
                        ),
                        DataNote(
                            "Вторая заметка",
                            "А вот это немного меньше, но не менее важная",
                            "16.10.2024 19:32",
                            "",
                            NoteGreen
                        )
                    ))
                }
            }
        }
    }
}