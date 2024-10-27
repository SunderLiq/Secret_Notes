package app.compose.secretnotes.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.ui.theme.DarkGreen20
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.Red80
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LogOut(navController: NavController) {
    val auth = Firebase.auth
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(45.dp))
            Text(text = "Current account: ${auth.currentUser?.email}", style = TextStyle(fontSize = 20.sp))
            Button(modifier = Modifier.padding(top = 5.dp),
                onClick = {
                    navController.navigate("ConfirmDeleteScreen")
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = Red80
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(text = "Delete account!!!", style = TextStyle(fontSize = 20.sp))
            }
        }
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Do you want logout? ", style = TextStyle(fontSize = 30.sp))
            Button(modifier = Modifier.padding(top = 25.dp),
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
        Button(
            onClick = {
                navController.navigate("mainScreen")
            },
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gray20
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back", style = TextStyle(fontSize = 15.sp))
        }
    }
}

internal fun deleteAccount(error: MutableState<String> ,navController: NavController,auth: FirebaseAuth, email: String, password: String) {
    try {
        auth.currentUser?.reauthenticate(EmailAuthProvider.getCredential(email, password))?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.delete()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        error.value = ""
                        Log.d("myLog", "Account $email was deleted!")
                        navController.navigate("SignUpScreen")
                    } else {
                        Log.d("myLog", "Failure delete account!")
                        error.value = "Incorrect value"
                    }
                }
            }
            else {
                error.value = "Incorrect value"
                Log.d("myLog", "Failure delete account!")
            }
        }
    } catch (e: Exception) {
        error.value = "Fields cannot be empty"
        Log.d("myLog", "Failure delete account!")

    }
}