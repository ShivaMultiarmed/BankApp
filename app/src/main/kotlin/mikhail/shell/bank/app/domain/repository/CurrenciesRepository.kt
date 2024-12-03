package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.Currency

interface CurrenciesRepository {
    fun getCurrencies(): Flow<List<Currency>>
}