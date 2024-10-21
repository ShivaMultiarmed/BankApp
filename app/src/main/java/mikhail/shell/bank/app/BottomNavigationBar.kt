package mikhail.shell.bank.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import mikhail.shell.bank.app.BottomNavigationItem.*

sealed class BottomNavigationItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)
{
    data object Home : BottomNavigationItem("Главная", "home", Icons.Rounded.Home)
    data object Wallet: BottomNavigationItem("Кошелек", "wallet", Icons.Rounded.ShoppingCart)
    data object Notifications: BottomNavigationItem("Уведомления", "notifications", Icons.Rounded.Notifications)
    data object Profile: BottomNavigationItem("Профиль", "person/4846", Icons.Rounded.AccountCircle)
}

val items = listOf(Home, Wallet, Notifications, Profile)

//@Preview
@Composable
fun BottomNavigationBar(navController: NavController)
{
    var selectedIcon by remember {
        mutableStateOf(0)
    }
    NavigationBar (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items.forEachIndexed { i, item ->
                NavigationBarItem(
                    selected = selectedIcon == i,
                    onClick = {
                        selectedIcon = i
                        navController.navigate(item.route)
                        {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(text = item.title, color = MaterialTheme.colorScheme.onBackground)
                    }
                )
            }
        }

    }
}