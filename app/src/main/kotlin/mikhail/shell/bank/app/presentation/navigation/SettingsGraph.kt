package mikhail.shell.bank.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import mikhail.shell.bank.app.presentation.settings.AdvancedSettingsScreen
import mikhail.shell.bank.app.presentation.settings.SettingsScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    navigation<Route.ProfileGraphRoute.SettingsGraphRoute>(
        startDestination = Route.ProfileGraphRoute.SettingsGraphRoute.SettingsScreenRoute::class
    )
    {
        val animationDuration = 300
        composable<Route.ProfileGraphRoute.SettingsGraphRoute.SettingsScreenRoute> {
            SettingsScreen(navController)
        }
        composable<Route.ProfileGraphRoute.SettingsGraphRoute.AdvancedSettingsRoute> {
            AdvancedSettingsScreen(navController)
        }
    }
}