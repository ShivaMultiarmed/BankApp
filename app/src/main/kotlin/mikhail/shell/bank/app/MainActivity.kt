package mikhail.shell.bank.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mikhail.shell.bank.app.presentation.navigation.Route
import mikhail.shell.bank.app.presentation.navigation.authGraph
import mikhail.shell.bank.app.presentation.navigation.homeRoutes
import mikhail.shell.bank.app.presentation.navigation.profileGraph
import mikhail.shell.bank.app.presentation.navigation.transactionGraph
import mikhail.shell.bank.app.sharedpreferences.getUserId
import mikhail.shell.bank.app.sharedpreferences.removeUserId
import mikhail.shell.bank.app.ui.theme.BankAppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BankAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Route.AuthGraph
                ) {
                    authGraph(navController)
                    homeRoutes(navController)
                    profileGraph(navController)
                    transactionGraph(navController)
                }
            }
        }
    }
}

