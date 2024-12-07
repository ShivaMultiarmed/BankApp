package mikhail.shell.bank.app.presentation.profile.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.ui.theme.spacing

@Preview
@Composable
fun UserDataSection(
    modifier: Modifier = Modifier,
    user: User = User()
)
{
    Column(
        modifier = modifier
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
    ) {
        SwitchingTextEdit("Имя", user.name)
        Dropdown(listOf("Мужчина", "Женщина"), user.gender)
    }
}