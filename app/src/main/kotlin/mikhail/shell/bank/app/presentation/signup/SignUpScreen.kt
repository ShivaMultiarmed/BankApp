package mikhail.shell.bank.app.presentation.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import mikhail.shell.bank.app.presentation.navigation.Route
import mikhail.shell.bank.app.domain.errors.AuthError
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.presentation.profile.sections.Dropdown
import mikhail.shell.bank.app.sharedpreferences.setUserId

@Composable
fun SignUpScreen(
    navController: NavController = rememberNavController(),
    state: SignUpState = SignUpState(),
    onSubmit: (String, String, User) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var email by remember {
            mutableStateOf("")
        }
        TextField(
            value = email,
            onValueChange = {
                email = it
            }
        )
        var name by remember {
            mutableStateOf("")
        }
        TextField(
            value = name,
            onValueChange = {
                name = it
            }
        )
        var gender by remember { mutableStateOf("Выберите пол") }
        Dropdown(
            listOf(
                "Выберите пол",
                "Мужчина",
                "Женщина"
            ),
            "Выберите пол",
            onValueChange = {
                gender = it
            }
        )
        var password by remember {
            mutableStateOf("")
        }
        TextField(
            value = password,
            onValueChange = {
                password = it
            }
        )
        Button(
            onClick = {
                onSubmit(email, password, User(name = name, gender = gender))
            }
        ) {
            Text("Зарегистрироваться")
        }
        if (state.error != null) {
            val errorMsg = when(state.error)
            {
                AuthError.EMAIL_EXISTS -> "Пользователь с таким e-mail уже существует."
                AuthError.EMAIL_EMPTY -> "Не заполнен e-mail"
                AuthError.PASSWORD_EMPTY -> "не заполнен пароль"
                AuthError.PASSWORD_INVALID -> "пароль слишком короткий"
                AuthError.MALFORMED_EMAIL -> "Некорректный e-mail"
                AuthError.UNEXPECTED_ERROR -> "Непредвиденная ошибка"
            }
            Text(errorMsg)
        } else if (state.userid != null) {
            Text("Вы успешно зарегистрировались")
            LaunchedEffect(true) {
                delay(1000)
                context.setUserId(state.userid)
                navController.navigate(Route.HomeScreenRoute)
            }
        }
    }
}