package app.compose.secretnotes.dataclasses

import androidx.compose.ui.graphics.Color

data class DataNote(
    val label: String,
    val text: String,
    val dateOfCreate: String,
    val dateOfChange: String,
    val noteColor: Color
){}
