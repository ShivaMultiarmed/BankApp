package mikhail.shell.bank.app

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import mikhail.shell.bank.app.presentation.home.HomeScreen
import mikhail.shell.bank.app.presentation.home.HomeViewModel
import mikhail.shell.bank.app.presentation.profile.ProfileScreen
import mikhail.shell.bank.app.presentation.profile.ProfileViewModel
import mikhail.shell.bank.app.presentation.settings.AdvancedSettingsScreen
import mikhail.shell.bank.app.presentation.settings.SettingsScreen
import mikhail.shell.bank.app.presentation.signin.SignInScreen
import mikhail.shell.bank.app.presentation.signin.SignInViewModel
import mikhail.shell.bank.app.presentation.utils.ApplicationScaffold
import mikhail.shell.bank.app.presentation.utils.ErrorComponent
import mikhail.shell.bank.app.presentation.utils.LoadingComponent
import mikhail.shell.bank.app.ui.theme.BankAppTheme
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Serializable
@Parcelize
data class User(
    val userid: String? = null,
    val name: String = "",
    val gender: String = ""
) : Parcelable


class AppNavType<T : Parcelable>(
    private val klass: Class<T>,
    private val serializer: KSerializer<T>
) : NavType<T>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): T? {
        return (
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    bundle.getParcelable(key, klass) as T
                else
                    bundle.getParcelable(key)
                )
    }

    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(serializer, value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)

    companion object {
        inline fun <reified T : Parcelable> getMap(serializer: KSerializer<T>): Map<KType, AppNavType<T>> {
            return mapOf(
                typeOf<T>() to AppNavType(T::class.java, serializer)
            )
        }
    }
}

@Serializable
sealed class Route {
    @Serializable
    data object SignInRoute : Route()

    @Serializable
    data object HomeScreenRoute : Route()

    @Serializable
    data object ProfileGraphRoute : Route() {
        @Serializable
        data class ProfileScreenRoute(val userid: String) : Route()

        @Serializable
        data object SettingsGraphRoute : Route() {
            @Serializable
            data object SettingsScreenRoute : Route()

            @Serializable
            data object AdvancedSettingsRoute : Route()
        }
    }

    @Serializable
    data object TransactionsScreenRoute : Route()

    @Serializable
    data object WalletScreenRoute : Route()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BankAppTheme {

                NavHost(
                    navController = navController,
                    startDestination = Route.SignInRoute,
                    /*enterTransition = {
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
                    }*/
                ) {
                    authRoutes(navController)
                    goToHome(navController)
                    goToProfile(navController)
                }
            }
        }
    }
}
fun NavGraphBuilder.authRoutes(
    navController: NavController
) {
    composable<Route.SignInRoute> {
        val viewModel = hiltViewModel<SignInViewModel>()
        if (viewModel.checkIfSignedIn())
            navController.navigate(Route.HomeScreenRoute)
        else {
            val signInState by viewModel.stateFlow.collectAsStateWithLifecycle()
            SignInScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                state = signInState,
                onSubmit = { email, password ->
                    viewModel.signIn(email, password)
                }
            )
        }
    }
}
fun NavGraphBuilder.goToHome(navController: NavController) {
    composable<Route.HomeScreenRoute> {
        val userid = getUserId()
        if (userid == null)
            navController.navigate(Route.SignInRoute)
        else {
            val homeViewModel = hiltViewModel<HomeViewModel, HomeViewModel.Factory> { factory ->
                factory.create(userid)
            }
            val screenState by homeViewModel.screenState.collectAsStateWithLifecycle()
            ApplicationScaffold (
                userid = userid,
                navController = navController
            ) { innerPadding ->
                HomeScreen(
                    navController = navController,
                    cards = screenState.cards,
                    balance = screenState.balance,
                    currencies = screenState.currencies,
                    tools = screenState.tools,
                    innerPadding = innerPadding
                )
            }
        }
    }
}

fun getUserId() = Firebase.auth.currentUser?.uid

fun NavGraphBuilder.goToProfile(navController: NavController) {
    navigation<Route.ProfileGraphRoute>(
        startDestination = Route.ProfileGraphRoute.ProfileScreenRoute::class
    )
    {
        composable<Route.ProfileGraphRoute.ProfileScreenRoute>(
            typeMap = AppNavType.Companion.getMap(User.serializer())
        ) { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<Route.ProfileGraphRoute.ProfileScreenRoute>()
            val profileViewModel =
                hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory ->
                    factory.create(args.userid)
                }
            val screenState by profileViewModel.screenState.collectAsStateWithLifecycle()
            val user = screenState.user
            if (user?.userid != null)
            {
                ApplicationScaffold (
                    navController = navController,
                    userid = user.userid
                ) { innerPadding ->
                    if (!screenState.isLoading) {
                        if (user != null) {
                            ProfileScreen(
                                navController = navController,
                                user = user,
                                innerPadding = innerPadding,
                                onSignOutClicked = {
                                    profileViewModel.signOut()
                                    navController.navigate(Route.SignInRoute)
                                }
                            )
                        } else {
                            ErrorComponent(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        LoadingComponent(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
        goToSettings(navController)
    }

}

fun NavGraphBuilder.goToSettings(navController: NavController) {
    navigation<Route.ProfileGraphRoute.SettingsGraphRoute>(
        startDestination = Route.ProfileGraphRoute.SettingsGraphRoute.SettingsScreenRoute::class
    )
    {
        val animationDuration = 300
        composable<Route.ProfileGraphRoute.SettingsGraphRoute.SettingsScreenRoute>(
            /*enterTransition = {
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
            }*/
        )
        {
            SettingsScreen(navController)
        }
        composable<Route.ProfileGraphRoute.SettingsGraphRoute.AdvancedSettingsRoute>(
            /*enterTransition = {
                scaleIn(
                    initialScale = 0f,
                    transformOrigin = TransformOrigin(0f,0f),
                    animationSpec = tween(1000, 0, LinearEasing)
                )
            }*/
        )
        {
            AdvancedSettingsScreen(navController)
        }
    }
}