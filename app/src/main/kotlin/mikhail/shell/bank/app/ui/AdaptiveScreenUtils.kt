package mikhail.shell.bank.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//@Composable
//fun rememberWindowInfo() : WindowInfo {
//    val configuration = LocalConfiguration.current
//    val screenWidth = configuration.screenWidthDp
//    val screenHeight = configuration.screenHeightDp
//    return WindowInfo(
//        screenWidthType = when {
//            screenWidth < 600 -> WindowInfo.WindowType.Compact
//            screenWidth < 840 -> WindowInfo.WindowType.Medium
//            else -> WindowInfo.WindowType.Large
//        },
//        screenHeightType = when {
//            screenHeight < 480 -> WindowInfo.WindowType.Compact
//            screenHeight < 900 -> WindowInfo.WindowType.Medium
//            else -> WindowInfo.WindowType.Large
//        },
//        width = screenWidth.dp,
//        height = screenHeight.dp
//    )
//}
//
//data class WindowInfo(
//    val screenWidthType: WindowType,
//    val screenHeightType: WindowType,
//    val width: Dp,
//    val height: Dp
//){
//    sealed class WindowType {
//        data object Compact: WindowType()
//        data object Medium: WindowType()
//        data object Large: WindowType()
//    }
//}

import mikhail.shell.bank.app.ui.ScreenInfo.ScreenType.*

data class ScreenInfo (
    val screenWidthType: ScreenType,
    val screenHeightType: ScreenType,
    val width: Dp,
    val height: Dp
) {
    enum class ScreenType (
        val width: Int,
        val height: Int
    ) {
        COMPACT(600, 480),
        MEDIUM(840, 900),
        LARGE(5000, 5000)
    }
}

@Composable
fun rememberScreenSize() : ScreenInfo
{
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp
    return ScreenInfo(
        screenWidthType = when {
            width < COMPACT.width -> COMPACT
            width < COMPACT.width -> MEDIUM
            else -> LARGE
        },
        screenHeightType = when {
            height < COMPACT.height -> COMPACT
            height < COMPACT.height -> MEDIUM
            else -> LARGE
        },
        width = width.dp,
        height = height.dp
    )
}