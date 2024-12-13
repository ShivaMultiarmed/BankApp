package mikhail.shell.bank.app.presentation.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.presentation.navigation.Route

@Composable
fun ApplicationScaffold(
    navController: NavController = rememberNavController(),
    primaryNavigationItem: BottomNavigationItem<Route> = BottomNavigationItem.Home,
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
            BottomNavigationBar(
                navController = navController,
                primaryItem = primaryNavigationItem
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}