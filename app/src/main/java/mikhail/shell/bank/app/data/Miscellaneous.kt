package mikhail.shell.bank.app.data

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ToggleableInfo(val title: String, var isChecked: Boolean)
@Composable
fun SectionsSpacer()
{
    Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
}