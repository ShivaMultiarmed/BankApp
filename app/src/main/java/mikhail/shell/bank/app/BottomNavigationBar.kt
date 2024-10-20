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

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector
)

val items = listOf(
    BottomNavigationItem("Главная", Icons.Rounded.Home),
    BottomNavigationItem("Кошелек", Icons.Rounded.ShoppingCart),
    BottomNavigationItem("Уведомления", Icons.Rounded.Notifications),
    BottomNavigationItem("Профиль", Icons.Rounded.AccountCircle)
)

@Preview
@Composable
fun BottomNavigationBar()
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
                        // selectedIcon = i
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