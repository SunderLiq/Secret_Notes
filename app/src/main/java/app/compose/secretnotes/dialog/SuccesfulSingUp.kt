package app.compose.secretnotes.dialog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun SignUpDialog() {
    val openDialog = remember { mutableStateOf(true) }
    when {
        openDialog.value -> {
            Dialog(onDismissRequest = { openDialog.value = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        text = "Welcome to Secret Notes!",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
var successfulSignUp = false