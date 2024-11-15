package mikhail.shell.bank.app.data.repository

import android.app.Application
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import mikhail.shell.bank.app.data.Card
import mikhail.shell.bank.app.data.CardSystem.MASTERCARD
import mikhail.shell.bank.app.data.CardSystem.VISA
import mikhail.shell.bank.app.data.CardType.PENSION
import mikhail.shell.bank.app.data.CardType.SAVINGS
import mikhail.shell.bank.app.data.remote.CardsApi
import mikhail.shell.bank.app.domain.repository.CardsRepository

class CardsRepositoryImpl(
    private val applicationContext: Application,
    private val cardsApi: CardsApi
) : CardsRepository {
    override suspend fun getCards(): List<Card> {
        delay(3000)
        cardsApi.getCards()
        return listOf(
            Card(VISA, SAVINGS, "2930 9343 2933 4242", 90345.6),
            Card(MASTERCARD, PENSION, "4534 3452 3453 1231", 20000.0)
        )
    }
}