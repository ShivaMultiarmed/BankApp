package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.CardsRepository
import javax.inject.Inject

class GetCards @Inject constructor(
    private val cardsRepository: CardsRepository
) {
    suspend operator fun invoke(userid: String) = cardsRepository.getCards(userid)
}