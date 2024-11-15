package mikhail.shell.bank.app.presentation.card

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mikhail.shell.bank.app.domain.repository.CardsRepository
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
): ViewModel() {
    suspend fun getCards() = cardsRepository.getCards()
}