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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.ui.theme.DarkGreen20
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.Green40
import app.compose.secretnotes.ui.theme.LightGreen20
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(navController: NavController) {
    val auth = Firebase.auth
    var userNameState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }
    var signUpError by remember { mutableStateOf("") }
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Sign up", style = TextStyle(fontSize = 25.sp))
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = userNameState,
            onValueChange = { userNameState = it },
            placeholder = {
                Text(
                    "Login",
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
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = passwordState,
            onValueChange = { passwordState = it },
            placeholder = {
                Text(
                    "Password",
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
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row { Button(onClick = {
            navController.navigate("SignInScreen")
        },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = Green40
            ),
            shape = RoundedCornerShape(15.dp)
        ){
            Text(text = "Sing In", style = TextStyle(fontSize = 20.sp))
        }
            Spacer(modifier = Modifier.width(40.dp))
            Button(onClick = {
                if(!signIn(auth, userNameState, passwordState, navController))
                    signUpError = "The password must contain from 6 to 20 characters of the Latin alphabet and numbers"
            },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = DarkGreen20
                ),
                shape = RoundedCornerShape(15.dp)
            ){
                Text(text = "Let's start!", style = TextStyle(fontSize = 20.sp))
            }
        }
        Text(text = signUpError, style = TextStyle(fontSize =  15.sp, textAlign = TextAlign.Center))
    }
}
private fun signIn(auth: FirebaseAuth, email: String, password: String, navController: NavController):Boolean {
    var itsOk = true
    try {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("myLog", "Sing up is successful!")
                    navController.navigate("mainScreen")
                }
                else {
                    Log.d("myLog", "Sing up is failure(. No exception")
                }
    }

        }
    catch (e: Exception) {
        Log.d("myLog", "Sing up is failure(")
        itsOk = false
    }
    return itsOk
}