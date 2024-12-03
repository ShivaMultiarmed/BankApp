package mikhail.shell.bank.app.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.CurrencyPound
import androidx.compose.material.icons.rounded.CurrencyYen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mikhail.shell.bank.app.data.remote.CurrenciesApi
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.repository.CurrenciesRepository
import javax.inject.Inject

class CurrenciesRepositoryImpl @Inject constructor(
    private val currenciesApi: CurrenciesApi
): CurrenciesRepository {
    private val currencies = listOf(
        Currency("USD", Icons.Rounded.CurrencyExchange, 90.0, 92.0),
        Currency("YEN", Icons.Rounded.CurrencyYen, 50.0, 54.0),
        Currency("GBP", Icons.Rounded.CurrencyPound, 105.0, 107.0),
    )
    override fun getCurrencies(): Flow<List<Currency>> = flow {
        //delay(2000)
        emit(currencies)
    }
}