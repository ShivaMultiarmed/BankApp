package mikhail.shell.bank.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.models.FinanceTool
import mikhail.shell.bank.app.presentation.home.sections.CardsSection
import mikhail.shell.bank.app.presentation.home.sections.CurrenciesSection
import mikhail.shell.bank.app.presentation.home.sections.FinanceSection
import mikhail.shell.bank.app.presentation.home.sections.WalletSection

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    cards: List<Card> = listOf(),
    balance: Double = 0.0,
    tools: List<FinanceTool> = listOf(),
    currencies: List<Currency> = listOf(),
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {

    val scrollState = rememberScrollState()
    Column (
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        WalletSection(balance)
        CardsSection(cards)
        FinanceSection(tools)
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(0.dp)
                .fillMaxWidth()
        )
        CurrenciesSection(currencies)
    }
}