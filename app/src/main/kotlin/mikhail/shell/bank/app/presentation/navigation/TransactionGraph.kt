package mikhail.shell.bank.app.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import mikhail.shell.bank.app.sharedpreferences.getUserId
import mikhail.shell.bank.app.presentation.transactions.AddTransactionScreen
import mikhail.shell.bank.app.presentation.transactions.AddTransactionViewModel
import mikhail.shell.bank.app.presentation.transactions.TransactionsScreen
import mikhail.shell.bank.app.presentation.utils.ApplicationScaffold
import mikhail.shell.bank.app.presentation.utils.BottomNavigationItem

fun NavGraphBuilder.transactionGraph(
    navController: NavController
) {
    navigation<Route.TransactionsGraph>(
        startDestination = Route.TransactionsGraph.TransactionListRoute::class,
    ) {
        composable<Route.TransactionsGraph.TransactionListRoute> {
            val userid = LocalContext.current.getUserId()
            if (userid == null)
                navController.navigate(Route.AuthGraph.SignInRoute)
            else {
                ApplicationScaffold(
                    navController = navController,
                    primaryNavigationItem = BottomNavigationItem.Transactions
                ) {
                    TransactionsScreen(
                        navController = navController
                    )
                }
            }
        }
        composable<Route.TransactionsGraph.AddTransactionRoute> {
            val userid = LocalContext.current.getUserId()
            if (userid == null)
                navController.navigate(Route.AuthGraph.SignInRoute)
            else {
                val viewModel = hiltViewModel<AddTransactionViewModel>()
                val state by viewModel.state.collectAsState()
                ApplicationScaffold(
                    navController = navController,
                    primaryNavigationItem = BottomNavigationItem.Transactions
                ) {
                    AddTransactionScreen(
                        navController = navController,
                        state = state,
                        onSubmit = { from, to, amount ->
                            viewModel.transferMoney(from, to, amount)
                        }
                    )
                }
            }
        }
    }
}