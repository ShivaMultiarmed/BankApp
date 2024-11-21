package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.Currency

interface CurrenciesRepository {
    suspend fun getCurrencies(): Flow<List<Currency>>
}