package app.compose.secretnotes.screens.main

import android.provider.Settings.Global.getString
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.R
import app.compose.secretnotes.R.drawable
import app.compose.secretnotes.ui.theme.Green80
import com.google.android.gms.auth.api.identity.BeginSignInRequest

var noteId: Int = 0 // count of notes in screen

@Composable
fun HomeScreen(navController: NavController) {
    Background()
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
            DefaultIconWhite(drawable.app_icon)
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Secret Notes",
                style = TextStyle(fontSize = 25.sp, color = Color.White)
            )
            IconButton(
                onClick = { navController.navigate("addNoteScreen") },
                modifier = Modifier.size(50.dp)
            ) {
                DefaultIconWhite(drawable.add_note_icon)
            }

        }
        NotesList(navController = navController)
    }
}

@Composable
fun DefaultIconWhite(res: Int) {
    Icon(
        painterResource(id = res),
        contentDescription = "",
        tint = Color.White,
        modifier = Modifier
            .padding(10.dp)
            .size(25.dp)
    )
}

@Composable
fun Background() {
    Image(
        painterResource(id = drawable.background),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}