package app.compose.secretnotes.login

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import app.compose.secretnotes.dialog.LoadingScreen
import app.compose.secretnotes.ui.theme.DarkGreen20
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.Green40
import app.compose.secretnotes.ui.theme.LightGreen20
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay


@Composable
fun ResetScreen(navController: NavController) {
    val auth = Firebase.auth
    var userNameState by remember { mutableStateOf("") }
    val signInError = remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Восстановить пароль", style = TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = userNameState,
            onValueChange = { userNameState = it },
            placeholder = {
                Text(
                    "Почта",
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
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = Green40
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(text = "Назад", style = TextStyle(fontSize = 20.sp))
            }
            Spacer(modifier = Modifier.width(40.dp))
            Button(
                onClick = {
                    isLoading = true
                    Reset(signInError, userNameState, navController)
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White, containerColor = DarkGreen20
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(text = "Отправить", style = TextStyle(fontSize = 20.sp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = signInError.value, style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center), modifier = Modifier.padding(top = 15.dp))
    }
    if (isLoading) {
        LoadingScreen(true)
        LaunchedEffect(Unit) {
            delay(2000)
            isLoading = false
        }
    }
}

private fun Reset(
    error: MutableState<String>,
    email: String,
    navController: NavController
) {
    try {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                        error.value = "Сообщение со сбросом отправлено на почту: $email"
                }
                else error.value = "Проверьте правильность введенной почты"
            }
    } catch (e: Exception) {
        error.value = "Поля не могут быть пустыми"
    }
}

