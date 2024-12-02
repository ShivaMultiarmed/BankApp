package mikhail.shell.bank.app.presentation.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ApplicationScaffold(
    navController: NavController = rememberNavController(),
    userid: String,
    content: @Composable (PaddingValues) -> Unit
)
{
    Scaffold(
        modifier = Modifier.Companion
            .fillMaxSize(),
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavigationBar(navController, userid)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}