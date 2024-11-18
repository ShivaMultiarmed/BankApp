package mikhail.shell.bank.app.presentation.card

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mikhail.shell.bank.app.usecases.GetCards
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCards
): ViewModel() {
    suspend fun getCards(userid: Long) = getCardsUseCase(userid)
}