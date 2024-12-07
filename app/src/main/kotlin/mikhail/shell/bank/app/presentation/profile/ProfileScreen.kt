package mikhail.shell.bank.app.presentation.profile

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.domain.models.User
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
    navController: NavController = rememberNavController(),
    user: User,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onSignOutClicked: () -> Unit
) {
    val windowInfo = calculateWindowSizeClass(activity = LocalContext.current as Activity)
    //val windowInfo = rememberWindowState()
    val screenInfo = rememberScreenSize()
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .verticalScroll(scrollState)
    ) {
        //if (windowInfo.screenWidthType is WindowInfo.WindowType.Compact)
        //if (screenInfo.screenWidthType == COMPACT)
        if (windowInfo.widthSizeClass == WindowWidthSizeClass.Compact) {
            AvatarSection(
                modifier = Modifier
                    .fillMaxWidth()
            )
            SectionsSpacer()
            UserDataSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                user = user
            )
        } else {
            Row(
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
                    user = user
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
            SettingsSection(navController, user)
            SyncSwitchSection()
            SectionsSpacer()
            LanguageChooserSection()
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                NotificationsChooserSection(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                )
                SettingsSection(navController, user)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SyncSwitchSection()
                LanguageChooserSection()
            }
        }
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Button (
                onClick = {
                    onSignOutClicked()
                }
            ) {
                Text("Выйти")
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