package mikhail.shell.bank.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

sealed class BottomNavigationItem<T : Route>(
    val route: T,
    val title: String,
    val icon: ImageVector,
    var count: Int = 0
) {
    data object Home : BottomNavigationItem<Route>(
        Route.HomeScreenRoute,
        "Главная",
        Icons.Rounded.Home)
    data object Wallet: BottomNavigationItem<Route>(
        Route.WalletScreenRoute,
        "Кошелек",
        Icons.Rounded.ShoppingCart
    )
    data object Notifications: BottomNavigationItem<Route>(
        Route.TransactionsScreenRoute,
        "Переводы",
        Icons.Rounded.Autorenew,
        4546
    )
    data object Profile: BottomNavigationItem<Route>(
        Route.ProfileGraphRoute.ProfileScreenRoute(
            User(404, "Glenn", "abcde", "Мужчина")
        ),
        "Профиль",
        Icons.Rounded.AccountCircle,
        1267)
}
val items = listOf(
    BottomNavigationItem.Home,
    BottomNavigationItem.Wallet,
    BottomNavigationItem.Notifications,
    BottomNavigationItem.Profile
)

//@Preview
@Composable
@Preview
fun BottomNavigationBar(navController: NavController = rememberNavController())
{
    var selectedIcon by remember {
        mutableStateOf(0)
    }
//    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val selectedItemNumber = items.indexOfFirst {
//        //it == currentBackStackEntry?.destination?.
//    }
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
                    colors = NavigationBarItemDefaults.colors(
//                        unselectedIconColor = Color.Cyan,
//                        selectedIconColor = Color.Blue,
                            indicatorColor = Color.Transparent,
//                        selectedTextColor = Color.Green,
//                        unselectedTextColor = Color.Yellow
                    ),
                    icon = {
                        if (item.count > 0)
                        {
                            BadgedBox(
                                badge = {
                                    Text(
                                        text = "${item.count}",
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.primary)
                                            .padding(2.dp),
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        lineHeight = 12.sp
                                    )
                                },
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    label = {
                        if (selectedIcon == i)
                        {
                            Text(
                                text = item.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 12.sp
                            )
                        }
                    }
                )
            }
        }

    }
}