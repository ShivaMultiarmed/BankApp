package mikhail.shell.bank.app.presentation.profile.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.domain.models.ToggleableInfo


@Preview
@Composable
fun LanguageChooserSection()
{
    val languages = remember {
        mutableStateListOf(
            ToggleableInfo("Русский", true),
            ToggleableInfo("English", false)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            "Язык:",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp)
        languages.forEachIndexed { i, language ->
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource () },
                        indication = null
                    ) {
                        languages.replaceAll { it.copy(isChecked = false) }
                        languages[i] = languages[i].copy(isChecked = true)
                    },
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = language.isChecked,
                    onClick = {
                        languages.replaceAll { it.copy(isChecked = false) }
                        languages[i] = languages[i].copy(isChecked = true)
                    }
                )
                Text(text = language.title)
            }
        }
    }
}