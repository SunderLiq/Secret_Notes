package app.compose.secretnotes.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.compose.secretnotes.login.deleteAccount
import app.compose.secretnotes.ui.theme.Gray20
import app.compose.secretnotes.ui.theme.LightGreen20
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DeleteAccountDialog(navController: NavController, auth: FirebaseAuth) {
    val openDialogDeleteAccount = remember { mutableStateOf(true) }
    val email = auth.currentUser?.email ?: ""
    val password = remember { mutableStateOf("") }
    val error = remember { mutableStateOf("") }

    when {
        openDialogDeleteAccount.value -> {
            AlertDialog(
                title = {
                    Column {
                        Text(
                            text = "Confirm the account deletion",
                            style = TextStyle(fontSize = 25.sp)
                        )
                        TextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            placeholder = {
                                Text(
                                    "Password",
                                    style = TextStyle(fontSize = 15.sp, color = Gray20),
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
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        deleteAccount(error, navController, auth, email, password.value)
                    }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openDialogDeleteAccount.value = false
                        navController.navigate("LogOutScreen")
                    }) {
                        Text(text = "Dismiss")
                    }
                },
                onDismissRequest = {
                    openDialogDeleteAccount.value = false
                    navController.navigate("LogOutScreen")
                                   },
                text = {
                    Text(text = error.value)
                }
            )
        }
    }
}