package app.compose.secretnotes.dataclasses

data class DataNote(
    val label: String = "",
    val text: String = "",
    val dateOfChange: String = "",
    val id: String = ""
){}
