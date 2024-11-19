package mikhail.shell.bank.app.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mikhail.shell.bank.app.usecases.GetCards
import mikhail.shell.bank.app.usecases.GetServices
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCardsUseCase: GetCards,
    private val getServicesUseCase: GetServices
): ViewModel() {
    suspend fun getCards(userid: Long) = getCardsUseCase(userid)
    suspend fun getServices(userid: Long) = getServicesUseCase(userid)
}