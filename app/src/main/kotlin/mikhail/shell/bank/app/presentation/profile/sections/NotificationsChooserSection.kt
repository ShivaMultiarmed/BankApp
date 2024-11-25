package mikhail.shell.bank.app.presentation.profile.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.domain.models.ToggleableInfo


@Preview
@Composable
fun NotificationsChooserSection(
    modifier: Modifier = Modifier
)
{
    val options = remember {
        mutableStateListOf(
            ToggleableInfo("Расходы", false),
            ToggleableInfo("Доходы", true)
        )
    }
    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            "Уведомления:",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp)
        options.forEachIndexed { i, option ->
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        options[i] = options[i].copy(isChecked = !options[i].isChecked)
                    },
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = option.isChecked,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.secondary
                    ),
                    onCheckedChange = {
                            isChecked -> options[i] = options[i].copy(isChecked = isChecked)
                    }
                )
                Text(text = option.title)
            }
        }
    }
}