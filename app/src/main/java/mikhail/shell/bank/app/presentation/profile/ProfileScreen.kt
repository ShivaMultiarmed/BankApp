package mikhail.shell.bank.app.presentation.profile

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.Route
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.models.SectionsSpacer
import mikhail.shell.bank.app.presentation.profile.sections.AvatarSection
import mikhail.shell.bank.app.presentation.profile.sections.LanguageChooserSection
import mikhail.shell.bank.app.presentation.profile.sections.NotificationsChooserSection
import mikhail.shell.bank.app.presentation.profile.sections.SettingsSection
import mikhail.shell.bank.app.presentation.profile.sections.SyncSwitchSection
import mikhail.shell.bank.app.presentation.profile.sections.UserDataSection
import mikhail.shell.bank.app.ui.rememberScreenSize

//@Preview
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController = rememberNavController(),
    innerPadding: PaddingValues = PaddingValues(0.dp)
)
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