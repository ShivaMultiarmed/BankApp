package mikhail.shell.bank.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopBar()
{
    TopAppBar(
        title = { Text("Banking App", fontSize = 16.sp) },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Color.Red,
//            titleContentColor = Color.Green
//        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    )
}