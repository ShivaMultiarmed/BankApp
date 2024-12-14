package mikhail.shell.bank.app.presentation.navigation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mikhail.shell.bank.app.presentation.home.HomeScreen
import mikhail.shell.bank.app.presentation.home.HomeViewModel
import mikhail.shell.bank.app.presentation.utils.ApplicationScaffold
import mikhail.shell.bank.app.presentation.utils.BottomNavigationItem
import mikhail.shell.bank.app.sharedpreferences.getUserId

fun NavGraphBuilder.homeRoutes(navController: NavController) {
    composable<Route.HomeScreenRoute> {
        val context = LocalContext.current
        val userid = context.getUserId() ?: ""
        val homeViewModel = hiltViewModel<HomeViewModel, HomeViewModel.Factory> { factory ->
            factory.create(userid)
        }
        val screenState by homeViewModel.screenState.collectAsStateWithLifecycle()
        ApplicationScaffold(
            navController = navController,
            primaryNavigationItem = BottomNavigationItem.Home
        ) { innerPadding ->
            HomeScreen(
                navController = navController,
                state = screenState,
                innerPadding = innerPadding
            )
        }

    }
}