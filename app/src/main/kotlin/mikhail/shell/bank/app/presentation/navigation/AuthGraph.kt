package mikhail.shell.bank.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.presentation.signin.SignInScreen
import mikhail.shell.bank.app.presentation.signin.SignInViewModel
import mikhail.shell.bank.app.presentation.signup.SignUpScreen
import mikhail.shell.bank.app.presentation.signup.SignUpViewModel
import mikhail.shell.bank.app.sharedpreferences.getUserId
import mikhail.shell.bank.app.sharedpreferences.setUserId

fun NavGraphBuilder.authGraph(
    navController: NavController
) {
    navigation<Route.AuthGraph>(
        startDestination = Route.AuthGraph.SignInRoute::class
    ) {
        composable<Route.AuthGraph.SignInRoute> {
            val viewModel = hiltViewModel<SignInViewModel>()
            val signInState by viewModel.stateFlow.collectAsStateWithLifecycle()
            var isFirstTimeLaunched by remember { mutableStateOf(true) }
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            SignInScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                state = signInState,
                onSubmit = { email, password ->
                    viewModel.signIn(email, password)
                },
                onGoogleSubmit = {
                    coroutineScope.launch {
                        viewModel.signInWithGoogle(it)
                    }
                }
            )
            if (context.getUserId() != null || signInState.userid != null) {
                if (isFirstTimeLaunched) {
                    if (context.getUserId() == null)
                        context.setUserId(signInState.userid!!)
                    isFirstTimeLaunched = false
                    navController.navigate(Route.HomeScreenRoute) {
                        popUpTo<Route.AuthGraph.SignInRoute> {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable<Route.AuthGraph.SignUpRoute> {
            val viewModel = hiltViewModel<SignUpViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            SignUpScreen(
                navController = navController,
                state = state,
                onSubmit = { email, password, user ->
                    viewModel.signUp(email, password, user)
                }
            )
        }
    }
}