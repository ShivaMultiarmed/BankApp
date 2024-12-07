package mikhail.shell.bank.app.presentation.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import mikhail.shell.bank.app.Route
import mikhail.shell.bank.app.domain.models.NetworkError
import mikhail.shell.bank.app.domain.models.SignInError

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    state: SignInState = SignInState(),
    navController: NavController = rememberNavController(),
    onSubmit: (String, String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (state.userid != null)
            navController.navigate(Route.HomeScreenRoute)
        var email by remember { mutableStateOf("mikhail.shell@yandex.ru") }
        var password by remember { mutableStateOf("abcdef") }
        TextField(
            value = email,
            onValueChange = {
                email = it
            }
        )
        TextField(
            value = password,
            onValueChange = {
                password = it
            }
        )
        Button(
            onClick = {
                onSubmit(email, password)
            }
        ) {
            Text("Войти")
        }
        if (state.error != null) {
            val text = when (state.error) {
                SignInError.CREDENTIALS_INVALID -> "Неправильные e-mail и/или пароль"
                NetworkError.NO_CONNECTION -> "Нет соединения"
                else -> "Непредвиденная ошибка"
            }
            Text(text)
        } else {
            if (state.userid != null) {
                Text("Вы успешно вошли")
            }
        }
        Text(
            text = "Зарегистрироваться?",
            Modifier.clickable {
                navController.navigate(Route.AuthGraph.SignUpRoute)
            }
        )
    }
}