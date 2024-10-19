package app.compose.secretnotes.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.compose.secretnotes.dataclasses.DataNote
import app.compose.secretnotes.ui.theme.Green40
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun NotesList() {
    val fs = Firebase.firestore
    val listNotes = remember {
        mutableStateOf(emptyList<DataNote>())
    }
    fs.collection("Notes").get().addOnCompleteListener{
        task ->
        if (task.isSuccessful){
            listNotes.value = task.result.toObjects(DataNote::class.java)
        }
            else task.exception
    }
    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn(Modifier.fillMaxSize()) {
        items(listNotes.value) { note ->
            Column(
                Modifier
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                    .fillMaxSize()
                    .background(Green40, shape = RoundedCornerShape(10.dp))
            )
            {
                Row(
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = note.label, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
                    Text(
                        text = note.dateOfChange,
                        style = TextStyle(fontSize = 15.sp)
                    )
                }
                Text(
                    text = note.text, style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                )
            }
        }

    }
}