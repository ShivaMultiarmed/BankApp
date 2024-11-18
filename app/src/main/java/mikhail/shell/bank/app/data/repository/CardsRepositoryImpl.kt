package mikhail.shell.bank.app.data.repository

import android.app.Application
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import mikhail.shell.bank.app.domain.Card
import mikhail.shell.bank.app.domain.CardSystem.MASTERCARD
import mikhail.shell.bank.app.domain.CardSystem.VISA
import mikhail.shell.bank.app.domain.CardType.PENSION
import mikhail.shell.bank.app.domain.CardType.SAVINGS
import mikhail.shell.bank.app.data.remote.CardsApi
import mikhail.shell.bank.app.domain.repository.CardsRepository
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val appContext: Application,
    private val cardsApi: CardsApi
) : CardsRepository {
    override suspend fun getCards(userid: Long): Flow<List<Card>> = flow {
        delay(3000)
        emit(cards)
    }
    companion object {
        private val cards =  listOf(
            Card(VISA, SAVINGS, "2930 9343 2933 4242", 90345.6),
            Card(MASTERCARD, PENSION, "4534 3452 3453 1231", 20000.0)
        )
    }
}