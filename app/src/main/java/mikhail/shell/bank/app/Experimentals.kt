package mikhail.shell.bank.app

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ExperimentalButton()
{
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Cyan,
            contentColor = Color.Red
        ),
        onClick = {}
    ){
        Text("Дополнительно")
    }
}