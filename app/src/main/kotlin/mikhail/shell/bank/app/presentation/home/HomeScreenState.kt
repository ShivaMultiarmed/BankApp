package mikhail.shell.bank.app.presentation.home

import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.models.FinanceTool

data class HomeScreenState (
    val cards: List<Card> = emptyList(),
    val tools: List<FinanceTool> = emptyList(),
    val currencies: List<Currency> = emptyList()
)
