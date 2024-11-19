package mikhail.shell.bank.app.ui

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.Route
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.Card
import mikhail.shell.bank.app.domain.FinanceTool
import mikhail.shell.bank.app.domain.SectionsSpacer
import mikhail.shell.bank.app.presentation.home.HomeViewModel
import mikhail.shell.bank.app.presentation.profile.ProfileViewModel
import mikhail.shell.bank.app.ui.sections.home.CardsSection
import mikhail.shell.bank.app.ui.sections.home.CurrenciesSection
import mikhail.shell.bank.app.ui.sections.home.FinanceSection
import mikhail.shell.bank.app.ui.sections.home.WalletSection
import mikhail.shell.bank.app.ui.sections.profile.AvatarSection
import mikhail.shell.bank.app.ui.sections.profile.LanguageChooserSection
import mikhail.shell.bank.app.ui.sections.profile.NotificationsChooserSection
import mikhail.shell.bank.app.ui.sections.profile.SettingsSection
import mikhail.shell.bank.app.ui.sections.profile.SyncSwitchSection
import mikhail.shell.bank.app.ui.sections.profile.UserDataSection

@Preview
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    if (!sharedPreferences.contains("userid"))
//        sharedPreferences.edit().putLong("userid", 505L).apply()
        sharedPreferences.edit {
            putLong("userid", 505L)
            apply()
        }
    val userid = sharedPreferences.getLong("userid", 0)
    val scrollState = rememberScrollState()

    val cardsList = remember { mutableStateListOf<Card>() }
    val toolsList = remember { mutableStateListOf<FinanceTool>() }
    LaunchedEffect(true) {
        if (cardsList.isNotEmpty())
            cardsList.clear()
        if (toolsList.isNotEmpty())
            toolsList.clear()
        launch (Dispatchers.IO) {
            val cardsFlow: Flow<List<Card>> = homeViewModel.getCards(userid)
            cardsFlow.collect { cards ->
                cardsList.addAll(cards)
            }
        }
        launch (Dispatchers.IO) {
            val servicesFlow = homeViewModel.getServices(userid)
            servicesFlow.collect { services ->
                toolsList.addAll(services)
            }
        }
    }

    Column (
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        WalletSection()
        CardsSection(cardsList)
        FinanceSection(toolsList)
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(0.dp)
                .fillMaxWidth()
        )
        CurrenciesSection()
    }
}

//@Preview
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController = rememberNavController(),
    innerPadding: PaddingValues = PaddingValues(0.dp))
{
    var user: User? by rememberSaveable { mutableStateOf(null) }
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    if (!sharedPreferences.contains("userid"))
//        sharedPreferences.edit().putLong("userid", 505L).apply()
        sharedPreferences.edit {
            putLong("userid", 505L)
            apply()
        }
    val userid = sharedPreferences.getLong("userid", 0)
    LaunchedEffect(true) {
        launch (Dispatchers.IO) {
            user = profileViewModel.getProfile(userid)
        }
    }
    val windowInfo = calculateWindowSizeClass(activity = LocalContext.current as Activity)
    //val windowInfo = rememberWindowState()
    val screenInfo = rememberScreenSize()
    val scrollState = rememberScrollState()
    if (user != null) {
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
            //if (windowInfo.screenWidthType is WindowInfo.WindowType.Compact)
            //if (screenInfo.screenWidthType == COMPACT)
            if (windowInfo.widthSizeClass == WindowWidthSizeClass.Compact)
            {
                AvatarSection(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                SectionsSpacer()
                UserDataSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    user = user!!
                )
            }
            else {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    AvatarSection(
                        modifier = Modifier
                            .fillMaxWidth(0.35f),
                        imgSize = 200.dp
                    )
                    UserDataSection(
                        modifier = Modifier
                            .fillMaxWidth(0.65f),
                        user = user!!
                    )
                }
                SectionsSpacer()
            }
            if (windowInfo.widthSizeClass == WindowWidthSizeClass.Compact)
            //if (screenInfo.screenWidthType == COMPACT)
            {
                NotificationsChooserSection(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                SectionsSpacer()
                SettingsSection(navController, user!!)
                SyncSwitchSection()
                SectionsSpacer()
                LanguageChooserSection()
            }
            else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    NotificationsChooserSection(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    )
                    SettingsSection(navController, user!!)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SyncSwitchSection()
                    LanguageChooserSection()
                }
            }
        }
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
        Button(onClick = { navController.navigate(Route.ProfileGraphRoute.SettingsGraphRoute.AdvancedSettingsRoute) }) {
            Text("Дополнительно")
        }
        Text(text = "Базовые настройки", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp)
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun AdvancedSettingsScreen(navController: NavController = rememberNavController()) {
    val scope = rememberCoroutineScope()
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO"
        )
    )
    //var permissionResult by remember { mutableStateOf(false) }
    val internetPermissionState = rememberPermissionState(android.Manifest.permission.INTERNET)
//    {
//        permissionResult = it
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        permissionState.permissions.forEach { permission ->
            when (permission.permission) {
                "android.permission.CAMERA" -> {
//                    scope.launch {
//                        permission.launchPermissionRequest()
//                    }
                    if (permission.status.isGranted) {
                        Text("Вы разрешили приложению пользоваться камерой.")
                    } else if(permission.status.shouldShowRationale) {
                        scope.launch {
                            permissionState.launchMultiplePermissionRequest()
                        }
                        Text("Вы временно запретили приложению пользоваться камерой.")
                    } else {
                        Text("Вы навсегда запретили приложению пользоваться камерой.")
                    }
                }
                "android.permission.RECORD_AUDIO" -> {
                    if (permission.status.isGranted) {
                        Text("Вы разрешили приложению записывать звук.")
                    }else{
                        scope.launch {
                            //permissionState.launchMultiplePermissionRequest()
                        }
                        Text("Вы запретили приложению записывать звук.")
                    }
                }
            }
        }
        if (internetPermissionState.status.isGranted)
        {
            Text("Вы разрешили использовать интернет")
        }
        else if (internetPermissionState.status.shouldShowRationale) {
            internetPermissionState.launchPermissionRequest()
            Text("Вы временно запретили использовать интернет")
        } else  {
            Text("Вы навсегда запретили использовать интернет")
        }
        Button(onClick = { navController.navigateUp() }) {
            Text("Назад")
        }
        Text(text = "Дополнительные настройки", color = MaterialTheme.colorScheme.primary, fontSize = 24.sp)
    }
}
//fun getCardSaver(): Saver<List<Card>, List<Map<String, Any>>>
//{
//    val cardSaver = Saver<Card, Map<String, Any>>(
//        save = {
//            mapOf(
//                "system" to it.system.name,
//                "type" to it.type.name,
//                "number" to it.number,
//                "balance" to it.balance
//            )
//        },
//        restore = {
//            Card(
//                system = CardSystem.valueOf(it["system"] as String),
//                type = CardType.valueOf(it["type"] as String),
//                number = it["number"] as String,
//                balance = it["balance"] as Double
//            )
//        }
//    )
//
//    val cardListSaver = Saver<List<Card>, List<Map<String, Any>>>(
//        save = { list -> list.map { cardSaver.save(it) } },
//        restore = { list -> list.map { cardSaver.restore(it) } }
//    )
//    return cardListSaver
//}