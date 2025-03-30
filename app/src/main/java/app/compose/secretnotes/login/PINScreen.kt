package app.compose.secretnotes.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.R
import app.compose.secretnotes.dataclasses.PINData
import app.compose.secretnotes.dialog.LoadingScreen
import app.compose.secretnotes.hashing.argon2
import app.compose.secretnotes.hashing.hashing
import app.compose.secretnotes.screens.main.DefaultIconWhite
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.LightGreen20
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.nio.charset.Charset

@Composable
fun PINScreen(navController: NavController) {
    val auth = Firebase.auth
    val fb = Firebase.firestore
    var pin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf("") }
    val pattern = remember { Regex("^\\d+\$") }
    IconButton(
        onClick = { navController.navigate("LogOutScreen") },
        modifier = Modifier
            .size(50.dp)
            .rotate(315f)
            .padding(top = 35.dp)
    ) {
        DefaultIconWhite(R.drawable.add_note_icon)
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 45.dp, start = 5.dp, end = 5.dp, bottom = 5.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(5.dp))
        Column {
            Text(text = "Измените свой пин-код")
            TextField(
                readOnly = true,
                value = pin,
                onValueChange = {
                    if ((it.isEmpty() || it.matches(pattern)) && it.length <= 4) {
                        pin = it
                    }
                },
                placeholder = {
                    Text(
                        "PIN",
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
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = LightGreen20,
                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Text(text = pinError, modifier = Modifier.padding(top = 15.dp))
        }
        Column { //Number keyboard
            Row { // 1 2 3
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "1"
                        }
                    }) {
                    Text(text = "1", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "2"
                        }
                    }) {
                    Text(text = "2", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "3"
                        }
                    }) {
                    Text(text = "3", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
            }
            Row { // 4 5 6
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "4"
                        }
                    }) {
                    Text(text = "4", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "5"
                        }
                    }) {
                    Text(text = "5", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "6"
                        }
                    }) {
                    Text(text = "6", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
            }
            Row { // 7 8 9
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "7"
                        }
                    }) {
                    Text(text = "7", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "8"
                        }
                    }) {
                    Text(text = "8", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "9"
                        }
                    }) {
                    Text(text = "9", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
            }
            Row { // <- 0 ok
                Button(modifier = Modifier
                    .padding(3.dp)
                    .size(105.dp, 90.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.isNotEmpty()) {
                            pin = pin.dropLast(1)
                        }
                    }) {
                    Image(
                        painterResource(R.drawable.backspace_icon),
                        contentDescription = "Confirm button",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(10.dp))
                }
                Button(modifier = Modifier
                    .padding(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    onClick = {
                        if ((pin.isEmpty() || pin.matches(pattern)) && pin.length <= 3) {
                            pin += "0"
                        }
                    }) {
                    Text(text = "0", style = TextStyle(fontSize = 30.sp), modifier = Modifier.padding(20.dp))
                }
                Button(
                    modifier = Modifier
                        .padding(3.dp)
                        .size(105.dp, 90.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(true),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),onClick = {
                            if (pin.length == 4) {
                                fb.collection("Notes").document("usersPIN")
                                    .collection(auth.currentUser?.uid.toString())
                                    .document("PIN_Hash").set(
                                        PINData(
                                            argon2(pin, auth.currentUser?.uid.toString())!!
                                        )
                                    )
                                navController.navigate("mainScreen")
                            } else pinError = "Пин-код должен содержать 4 символа"
                    }) {
                    Image(
                        painterResource(R.drawable.confirm_icon),
                        contentDescription = "Confirm button",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.padding(10.dp))
                }
            }
        }
        Button(onClick = { navController.navigate("LogOutScreen") }, Modifier.padding(bottom = 10.dp)) {
            Text(text = "Back")
        }
    }
}