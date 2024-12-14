package mikhail.shell.bank.app.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.CurrencyPound
import androidx.compose.material.icons.rounded.CurrencyYen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import mikhail.shell.bank.app.data.dto.CurrencyDto
import mikhail.shell.bank.app.data.remote.CurrenciesApi
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.repository.CurrenciesRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

class CurrenciesRepositoryWithRestApi @Inject constructor(
    private val currenciesApi: CurrenciesApi
): CurrenciesRepository {
    override fun getCurrencies(): Flow<List<Currency>> {
        val currenciesFlow = MutableStateFlow<List<Currency>>(listOf())
        fixedRateTimer(
            name = "fetching_currencies",
            initialDelay = 0,
            period = 3600000
        ) {
            currenciesApi.fetchCurrencies(
                apiKey = "ede68b5e84a3711ed8564f88",
                auth = "ApiKey ede68b5e84a3711ed8564f88",
                from = "RUB"
            ).enqueue(object : Callback<CurrencyDto> {
                override fun onResponse(request: Call<CurrencyDto>, response: Response<CurrencyDto>) {
                    if (response.code() in 200..299){
                        val dto = response.body()
                        if (dto != null) {
                            val vacancies = mutableListOf<Currency>()
                            val vacanciesNamings = mapOf(
                                "USD" to Icons.Rounded.CurrencyExchange,
                                "GBP" to Icons.Rounded.CurrencyPound,
                            )
                            vacanciesNamings.forEach { k, v ->
                                if (dto.conversion_rates.containsKey(k))
                                    vacancies.add(Currency(k, v, 1 / dto.conversion_rates[k]!!, 1 / dto.conversion_rates[k]!!))
                            }
                            currenciesFlow.value = vacancies
                        }
                    }
                }
                override fun onFailure(request: Call<CurrencyDto>, t: Throwable) {
                    currenciesFlow.value = currenciesFlow.value
                }
            })

        }
        return currenciesFlow
    }
}