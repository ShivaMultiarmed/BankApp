package mikhail.shell.bank.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.Route
import mikhail.shell.bank.app.data.SectionsSpacer
import mikhail.shell.bank.app.ui.sections.home.CardsSection
import mikhail.shell.bank.app.ui.sections.home.CurrenciesSection
import mikhail.shell.bank.app.ui.sections.home.FinanceSection
import mikhail.shell.bank.app.ui.sections.home.WalletSection
import mikhail.shell.bank.app.ui.sections.profile.AvatarSection
import mikhail.shell.bank.app.ui.sections.profile.LanguageChooserSection
import mikhail.shell.bank.app.ui.sections.profile.NotificationsChooserSection
import mikhail.shell.bank.app.ui.sections.profile.SyncSwitchSection
import mikhail.shell.bank.app.ui.sections.profile.UserDataSection

@Preview
@Composable
fun HomeScreen(navController: NavController = rememberNavController(), innerPadding: PaddingValues = PaddingValues(0.dp))
{
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        WalletSection()
        CardsSection()
        FinanceSection()
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(0.dp)
                .fillMaxWidth()
        )
        CurrenciesSection()
    }
}

@Preview
@Composable
fun ProfileScreen(navController: NavController = rememberNavController(), userid: Long = 1L, innerPadding: PaddingValues = PaddingValues(0.dp))
{
    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .verticalScroll(scrollState)
    )
    {
        AvatarSection()
        UserDataSection()
        SectionsSpacer()
        NotificationsChooserSection()
        SectionsSpacer()
        Column(modifier = Modifier.fillMaxWidth())
        {
            Text(text = "This is a user number $userid")
           Button(onClick = {
                navController.navigate("settings")
           }) {
               Text("Настройки")
           }
        }
        SyncSwitchSection()
        SectionsSpacer()
        LanguageChooserSection()
    }
}
@Preview
@Composable
fun SettingsScreen(navController: NavController = rememberNavController())
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { navController.navigate(Route.AdvancedSettingsRoute) }) {
            Text("Дополнительно")
        }
        Text(text = "Базовые настройки", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp)
    }
}
@Preview
@Composable
fun AdvancedSettingsScreen(navController: NavController = rememberNavController())
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { navController.navigateUp() }) {
            Text("Назад")
        }
        Text(text = "Дополнительные настройки", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp)
    }
}