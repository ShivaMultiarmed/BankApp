package mikhail.shell.bank.app.usecases

import mikhail.shell.bank.app.domain.repository.CardsRepository
import javax.inject.Inject

class GetCards @Inject constructor(
    private val cardsRepository: CardsRepository
) {
    //@Inject lateinit var cardsRepository: CardsRepository
    suspend operator fun invoke(userid: Long) = cardsRepository.getCards(userid)
}