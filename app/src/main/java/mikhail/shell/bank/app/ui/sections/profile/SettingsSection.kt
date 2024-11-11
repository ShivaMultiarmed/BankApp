package mikhail.shell.bank.app.ui.sections.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.Route
import mikhail.shell.bank.app.User

@Composable
fun SettingsSection(
    navController: NavController = rememberNavController(),
    user: User = User(
        0L, "", "", ""
    )
) {
    Column(modifier = Modifier.fillMaxWidth())
    {
        Text(text = "This is ${user.name}'s profile")
        Button(onClick = {
            navController.navigate(Route.ProfileGraphRoute.SettingsGraphRoute.SettingsScreenRoute)
        }) {
            Text("Настройки")
        }
    }
}