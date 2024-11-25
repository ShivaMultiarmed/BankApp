package mikhail.shell.bank.app.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

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