package mikhail.shell.bank.app.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mikhail.shell.bank.app.domain.usecases.GetCards
import mikhail.shell.bank.app.domain.usecases.GetCurrencies
import mikhail.shell.bank.app.domain.usecases.GetServices
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCardsUseCase: GetCards,
    private val getServicesUseCase: GetServices,
    private val getCurrenciesUseCase: GetCurrencies
): ViewModel() {
    suspend fun getCards(userid: Long) = getCardsUseCase(userid)
    suspend fun getServices(userid: Long) = getServicesUseCase(userid)
    suspend fun getCurrencies() = getCurrenciesUseCase()
}