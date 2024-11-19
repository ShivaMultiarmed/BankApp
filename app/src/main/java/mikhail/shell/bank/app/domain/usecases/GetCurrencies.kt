package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.CurrenciesRepository
import javax.inject.Inject

class GetCurrencies @Inject constructor(
    private val currenciesRepository: CurrenciesRepository
) {
    suspend operator fun invoke() = currenciesRepository.getCurrencies()
}