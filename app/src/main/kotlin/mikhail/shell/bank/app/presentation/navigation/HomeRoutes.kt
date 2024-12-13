package mikhail.shell.bank.app.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mikhail.shell.bank.app.presentation.home.HomeScreen
import mikhail.shell.bank.app.presentation.home.HomeViewModel
import mikhail.shell.bank.app.presentation.utils.ApplicationScaffold
import mikhail.shell.bank.app.sharedpreferences.getUserId

fun NavGraphBuilder.homeRoutes(navController: NavController) {
    composable<Route.HomeScreenRoute> {
        val userid = getUserId() ?: ""
        val homeViewModel = hiltViewModel<HomeViewModel, HomeViewModel.Factory> { factory ->
            factory.create(userid)
        }
        val screenState by homeViewModel.screenState.collectAsStateWithLifecycle()
        ApplicationScaffold(
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