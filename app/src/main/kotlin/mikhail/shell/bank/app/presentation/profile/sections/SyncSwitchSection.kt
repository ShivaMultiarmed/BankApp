package mikhail.shell.bank.app.presentation.profile.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun SyncSwitchSection()
{
    var isSyncEnabled by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Switch(
            modifier = Modifier,
            checked = isSyncEnabled,
            onCheckedChange = { isChecked -> isSyncEnabled = isChecked },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondary
            ),
            thumbContent = {
                Icon(
                    imageVector = if (isSyncEnabled) Icons.Rounded.Check else Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        )
        Text(
            "Включить синхронизацию",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }
}