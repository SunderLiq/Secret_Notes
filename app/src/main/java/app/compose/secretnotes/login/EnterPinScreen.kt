package app.compose.secretnotes.login

import android.util.Log
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.R
import app.compose.secretnotes.dataclasses.PINData
import app.compose.secretnotes.screens.main.DefaultIconWhite
import app.compose.secretnotes.ui.theme.DarkGreen20
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.LightGreen20
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun EnterPinScreen(navController: NavController) {
    val auth = Firebase.auth
    val fb = Firebase.firestore
    var PIN by remember { mutableStateOf("") }
    var PinError by remember { mutableStateOf("") }
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
        modifier = Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Enter your PIN")
            TextField(
                value = PIN,
                onValueChange = {
                    if ((it.isEmpty() || it.matches(pattern)) && it.length <= 4) {
                        PIN = it
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
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Gray20
                ),
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
            Button(onClick = {
                fb.collection("Notes").document("usersPIN")
                    .collection(auth.currentUser?.uid.toString())
                    .document("PIN").get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (PIN == task.result.toObject(PINData::class.java)?.pin!!) {
                                PinError = ""
                                navController.navigate("mainScreen")
                            } else {
                                PinError = "Enter correct pin"
                            }
                        } else task.exception
                    }
            }) {
                Text("OK")
            }
            Text(text = PinError, modifier = Modifier.padding(top = 15.dp))
        }
        Button(
            modifier = Modifier
                .padding(top = 25.dp)
                .fillMaxWidth(),
            onClick = {
                auth.signOut()
                navController.navigate("SignInScreen")
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = DarkGreen20
            ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = "Logout", style = TextStyle(fontSize = 20.sp))
        }
    }
}