package mikhail.shell.bank.app.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.presentation.navigation.Route
import mikhail.shell.bank.app.sharedpreferences.getUserId

sealed class BottomNavigationItem<T : Route>(
    val route: T,
    val title: String,
    val icon: ImageVector,
    var count: Int = 0
) {
    data object Home : BottomNavigationItem<Route>(
        Route.HomeScreenRoute,
        "Главная",
        Icons.Rounded.Home
    )

    data object Wallet : BottomNavigationItem<Route>(
        Route.WalletScreenRoute,
        "Кошелек",
        Icons.Rounded.AccountBalanceWallet
    )

    data object Transactions : BottomNavigationItem<Route>(
        Route.TransactionsGraph,
        "Переводы",
        Icons.Rounded.Autorenew,
        4546
    )

    data class Profile(val userid: String) : BottomNavigationItem<Route>(
        Route.ProfileGraphRoute.ProfileScreenRoute(userid),
        "Профиль",
        Icons.Rounded.AccountCircle,
        1267
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavController = rememberNavController(),
    primaryItem: BottomNavigationItem<Route> = BottomNavigationItem.Home
) {
    val userid = LocalContext.current.getUserId()?:""
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Wallet,
        BottomNavigationItem.Transactions,
        BottomNavigationItem.Profile(userid)
    )
//    var selectedIcon by rememberSaveable {
//        mutableIntStateOf(items.indexOf(primaryItem))
//    }
    val selectedIcon by rememberSaveable {
        mutableIntStateOf(items.indexOf(primaryItem))
    }
//    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val selectedItemNumber = items.indexOfFirst {
//        //it == currentBackStackEntry?.destination?.
//    }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items.forEachIndexed { i, item ->
                NavigationBarItem(
                    selected = selectedIcon == i,
                    onClick = {
                        // selectedIcon = i
                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.findStartDestination().id) {
//                                // inclusive = true
//                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                    ),
                    icon = {
                        if (item.count > 0) {
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
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    label = {
                        if (selectedIcon == i) {
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