package app.compose.secretnotes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.compose.secretnotes.ui.theme.Green40
import app.compose.secretnotes.ui.theme.Green80

@Composable
fun Header (){
    Column (modifier = Modifier.fillMaxSize().padding(top = 45.dp).background(Green40)){
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(Green80),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ){
            Text(modifier = Modifier.padding(5.dp), text = "Secret Notes", style = TextStyle(fontSize = 20.sp, color = Color.White))
        }
    }
}