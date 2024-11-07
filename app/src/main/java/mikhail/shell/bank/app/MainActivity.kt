package mikhail.shell.bank.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import mikhail.shell.bank.app.ui.AdvancedSettingsScreen
import mikhail.shell.bank.app.ui.HomeScreen
import mikhail.shell.bank.app.ui.ProfileScreen
import mikhail.shell.bank.app.ui.SettingsScreen
import mikhail.shell.bank.app.ui.theme.BankAppTheme

@Serializable
sealed class Route{
    @Serializable
    object HomeScreenRoute:Route() {}
    @Serializable
    object ProfileGraphRoute:Route() {}
    @Serializable
    data class ProfileScreenRoute(val userid: Long = 100500):Route() {}
    @Serializable
    object NotificationsScreenRoute:Route() {}
    @Serializable
    object WalletScreenRoute:Route() {}
    @Serializable
    object SettingsScreenRoute:Route()
    @Serializable
    object SettingsGraphRoute:Route()
    @Serializable
    object AdvancedSettingsRoute:Route()
}


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
                        startDestination = Route.HomeScreenRoute,
                        enterTransition = {
                            slideIntoContainer(
                                towards = Up,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 0,
                                    easing = LinearEasing
                                )
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = Up,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 0,
                                    easing = LinearEasing
                                )
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = Down,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 0,
                                    easing = LinearEasing
                                )
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = Down,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 0,
                                    easing = LinearEasing
                                )
                            )
                        }
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
    composable<Route.HomeScreenRoute> {
        HomeScreen(navController, innerPadding)
    }
}
fun NavGraphBuilder.goToProfile(navController: NavController, innerPadding: PaddingValues)
{
    composable<Route.ProfileScreenRoute>
    { navBackStackEntry ->
        //val userid = navBackStackEntry.arguments?.getLong("userid")
        val userid = 100500L
        ProfileScreen(navController, userid!!, innerPadding)
    }
    /*navigation<Route.ProfileGraphRoute>(
        startDestination = Route.ProfileScreenRoute
    )
    {
        composable<Route.ProfileScreenRoute>
        { navBackStackEntry ->
            //val userid = navBackStackEntry.arguments?.getLong("userid")
            val userid = 100500L
            ProfileScreen(navController, userid!!, innerPadding)
        }
        goToSettings(navController, innerPadding)
    }*/

}
fun NavGraphBuilder.goToSettings(navController: NavController, innerPadding: PaddingValues)
{
    navigation<Route.SettingsGraphRoute>(
        startDestination = Route.SettingsScreenRoute
    )
    {
        val animationDuration = 300
        composable<Route.SettingsScreenRoute>(
            enterTransition = {
                fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(
                        durationMillis = animationDuration,
                        delayMillis = 0,
                        easing = FastOutSlowInEasing
                    )
                ) + slideIntoContainer(
                    towards = Start,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    targetAlpha = 1f,
                    animationSpec = tween(
                        durationMillis = animationDuration,
                        delayMillis = 0,
                        easing = FastOutSlowInEasing
                    )
                ) + slideOutOfContainer(
                    towards = End,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }
        )
        {
            SettingsScreen(navController)
        }
        composable<Route.AdvancedSettingsRoute>(
            enterTransition = {
                scaleIn(
                    initialScale = 0f,
                    transformOrigin = TransformOrigin(0f,0f),
                    animationSpec = tween(1000, 0, LinearEasing)
                )
            }
        )
        {
            AdvancedSettingsScreen(navController)
        }
    }
}