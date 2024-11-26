package mikhail.shell.bank.app.data.repository

import app.cash.turbine.test
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.repository.CardsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CardsRepositoryWithFirebaseTests {
    @get:Rule
    val injectRule = HiltAndroidRule(this)
    @Inject
    lateinit var db: FirebaseDatabase
    lateinit var cardsRepository: CardsRepository
    @Before
    fun initialize()
    {
        injectRule.inject()
        cardsRepository = CardsRepositoryWithFirebase(db)
    }
    @Test
    fun testCreatingCard() = runBlocking {
        val card = Card(505L, CardSystem.VISA, CardType.SAVINGS)
        val addedCard = cardsRepository.createCard(505L, card)
        Assert.assertEquals(card, addedCard)
    }
    @Test
    fun testUpdatingCard() = runBlocking {
        var card = cardsRepository.getCard(4128291795121080)
        card = card.copy(balance = card.balance + 1000)
        val updatedCard = cardsRepository.updateCard(card)
        Assert.assertEquals(card, updatedCard)
    }
    @Test
    fun testDeletingCard() = runBlocking {
        val cardNumber = 3437609883453695
        cardsRepository.deleteCard(cardNumber)
    }
    @Test
    fun testFetchingCardsByUserId() = runBlocking {
        val userid = 505L
        val cards = cardsRepository.getCards(userid).test {
            Assert.assertNotNull(awaitItem())
        }
    }
}