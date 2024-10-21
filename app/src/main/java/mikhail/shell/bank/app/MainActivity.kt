package mikhail.shell.bank.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.ui.AdvancedSettingsScreen
import mikhail.shell.bank.app.ui.SettingsScreen
import mikhail.shell.bank.app.ui.HomeScreen
import mikhail.shell.bank.app.ui.ProfileScreen
import mikhail.shell.bank.app.ui.theme.BankAppTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BankAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ){
                        goToHome(navController,innerPadding)
                        goToProfile(navController, innerPadding)
                    }
                }

            }
        }
    }
}

fun NavGraphBuilder.goToHome(navController: NavController, innerPadding: PaddingValues)
{
    composable("home") {
        HomeScreen(navController, innerPadding)
    }
}
fun NavGraphBuilder.goToProfile(navController: NavController, innerPadding: PaddingValues)
{
    navigation(
        route = "profile",
        startDestination = "person/{userid}"
    )
    {
        composable("person/{userid}")
        { navBackStackEntry ->
            val userid = navBackStackEntry.arguments?.getLong("userid")
            ProfileScreen(navController, userid!!, innerPadding)
        }
        navigation(startDestination = "settings", route = "settings_graph")
        {
            composable("settings")
            {
                SettingsScreen(navController)
            }
            composable("settings/advanced")
            {
                AdvancedSettingsScreen(navController)
            }
        }

    }

}






