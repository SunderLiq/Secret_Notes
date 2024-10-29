package app.compose.secretnotes.screens.main

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.R
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.Green80
import app.compose.secretnotes.ui.theme.LightGreen20
import app.compose.secretnotes.ui.theme.LightGreen80
import app.compose.secretnotes.ui.theme.DarkGreen20
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

var label = "" // Display label note in TextField
var text = "" // Display text note in TextField

@SuppressLint("SimpleDateFormat")
@Composable
fun EditNote(navController: NavController) {
    val fs = Firebase.firestore
    val auth = Firebase.auth

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen80)
    )
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Green80, shape = RoundedCornerShape(10.dp))
                .padding(top = 45.dp)
                .alpha(0.8f)
                .clip(RoundedCornerShape(20.dp)),
            Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Leave to HomeScreen
            Spacer(modifier = Modifier.width(50.dp))
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Edit Note",
                style = TextStyle(fontSize = 25.sp, color = Color.White)
            )
            IconButton(
                onClick = { navController.navigate("mainScreen") },
                modifier = Modifier
                    .size(50.dp)
                    .rotate(315f)
            ) {
                DefaultIconWhite(R.drawable.add_note_icon)
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(LightGreen20), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                EditLabelField()
                EditTextField()
            }
            Row (modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                // Delete note
                Button(onClick = {
                    fs.collection("Notes").document("usersNotes").collection(auth.currentUser?.email.toString()).document(
                        noteEditId).delete()
                    navController.navigate("mainScreen")
                },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, containerColor = DarkGreen20
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = "Delete", style = TextStyle(fontSize = 20.sp))
                }
                // Save note
                Button(
                    onClick = {
                        val sdf = SimpleDateFormat("yyyy-M-dd HH:mm")
                        val currentDate = sdf.format(Date())
                        fs.collection("Notes").document("usersNotes").collection(auth.currentUser?.email.toString()).document(
                            noteEditId).update(
                            "dateOfChange", currentDate,
                            "label", label,
                            "text", text
                        )
                        navController.navigate("mainScreen")
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White, containerColor = Green80
                    ),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = "Save", style = TextStyle(fontSize = 20.sp))
                }
            }

        }
    }
}

@Composable
fun EditLabelField() {
    var labelText by remember { mutableStateOf(label) }
    TextField(
        value = labelText,
        onValueChange = { labelText = it },
        placeholder = {
            Text(
                "Название",
                style = TextStyle(fontSize = 25.sp, color = Gray20),
                textAlign = TextAlign.Center
            )
        },
        singleLine = true,
        textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Gray20),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightGreen20,
            focusedContainerColor = LightGreen20,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp)
    )
    label = labelText
}

@Composable
fun EditTextField() {
    var noteText by remember { mutableStateOf(text) }

    TextField(
        value = noteText,
        onValueChange = { noteText = it },
        placeholder = {
            Text(text = "Текст", style = TextStyle(fontSize = 20.sp, color = Gray20))
        },
        textStyle = TextStyle(fontSize = 20.sp, color = Gray20),
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightGreen20,
            focusedContainerColor = LightGreen20,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp),
    )
    text = noteText
}