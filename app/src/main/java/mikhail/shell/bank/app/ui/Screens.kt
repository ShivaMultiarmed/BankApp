package mikhail.shell.bank.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mikhail.shell.bank.app.ui.sections.home.CardsSection
import mikhail.shell.bank.app.ui.sections.home.CurrenciesSection
import mikhail.shell.bank.app.ui.sections.home.FinanceSection
import mikhail.shell.bank.app.ui.sections.home.WalletSection
import mikhail.shell.bank.app.ui.sections.profile.AvatarSection
import mikhail.shell.bank.app.ui.sections.profile.UserDataSection

@Preview
@Composable
fun HomeScreen(innerPadding: PaddingValues = PaddingValues(0.dp))
{
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        WalletSection()
        CardsSection()
        FinanceSection()
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(0.dp)
                .fillMaxWidth()
        )
        CurrenciesSection()
    }
}



@Preview
@Composable
fun ProfileScreen(innerPadding: PaddingValues = PaddingValues(0.dp))
{
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    )
    {
        AvatarSection()
        UserDataSection()
    }
}