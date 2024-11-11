package mikhail.shell.bank.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo() : WindowInfo {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    return WindowInfo(
        screenWidthType = when {
            screenWidth < 600 -> WindowInfo.WindowType.Compact
            screenWidth < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Large
        },
        screenHeightType = when {
            screenHeight < 480 -> WindowInfo.WindowType.Compact
            screenHeight < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Large
        },
        width = screenWidth.dp,
        height = screenHeight.dp
    )
}

data class WindowInfo(
    val screenWidthType: WindowType,
    val screenHeightType: WindowType,
    val width: Dp,
    val height: Dp
){
    sealed class WindowType {
        data object Compact: WindowType()
        data object Medium: WindowType()
        data object Large: WindowType()
    }
}