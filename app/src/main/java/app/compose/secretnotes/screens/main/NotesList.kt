package app.compose.secretnotes.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.compose.secretnotes.dataclasses.DataNote

@Composable
fun NotesList (list: List<DataNote>) {
    LazyColumn(Modifier.fillMaxSize()) {
        itemsIndexed(
            list
        ) { _, item ->
            NoteItem(item)
        }

    }
}

@Composable
fun NoteItem (item: DataNote) {
    Column (Modifier.padding(bottom = 5.dp).fillMaxSize().
        background(item.noteColor, shape = RoundedCornerShape(10.dp))
    )
    {
        Row (Modifier.padding(10.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = item.label, style = TextStyle(fontSize = 20.sp))
            Text(text = item.dateOfCreate, style = TextStyle(fontSize = 10.sp))
        }
        Text(text = item.text, style = TextStyle(fontSize = 15.sp),
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp))
    }
}