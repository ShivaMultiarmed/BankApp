package mikhail.shell.bank.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.presentation.profile.ProfileScreen
import mikhail.shell.bank.app.presentation.profile.ProfileViewModel
import mikhail.shell.bank.app.presentation.utils.ApplicationScaffold
import mikhail.shell.bank.app.presentation.utils.ErrorComponent
import mikhail.shell.bank.app.presentation.utils.LoadingComponent

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation<Route.ProfileGraphRoute>(
        startDestination = Route.ProfileGraphRoute.ProfileScreenRoute::class
    ) {
        composable<Route.ProfileGraphRoute.ProfileScreenRoute>(
            typeMap = AppNavType.getMap(User.serializer())
        ) { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<Route.ProfileGraphRoute.ProfileScreenRoute>()
            val profileViewModel =
                hiltViewModel<ProfileViewModel, ProfileViewModel.Factory> { factory ->
                    factory.create(args.userid)
                }
            val screenState by profileViewModel.screenState.collectAsStateWithLifecycle()
            val user = screenState.user
            if (user?.userid != null) {
                ApplicationScaffold(
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
                                    navController.navigate(Route.AuthGraph.SignInRoute) {
                                        popUpTo<Route.ProfileGraphRoute.ProfileScreenRoute> {
                                            inclusive = true
                                        }
                                    }
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
        settingsGraph(navController)
    }

}