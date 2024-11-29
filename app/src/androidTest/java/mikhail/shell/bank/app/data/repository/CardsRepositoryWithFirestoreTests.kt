package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.repository.CardsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CardsRepositoryWithFirestoreTests {
    @get:Rule
    val rule = HiltAndroidRule(this)
    @Inject
    lateinit var db: FirebaseFirestore
    lateinit var repository: CardsRepository
    @Before
    fun initialize () {
        rule.inject()
        repository = CardsRepositoryWithFireStore(db)
    }
    @Test
    fun testCreatingCard() = runBlocking {
        val card = Card(userid = "505")
        val createdCard = repository.createCard(card)
        Assert.assertEquals(card, createdCard)
    }
    @Test
    fun testUpdatingCard() = runBlocking {
        val card = repository.getCard(5840380348920859)
        val updatedCard = repository.updateCard(card.copy(balance = card.balance + 1000))
        Assert.assertNotEquals(card, updatedCard)
    }
    @Test
    fun testDeletingCard() = runBlocking {
        repository.deleteCard(5840380348920859)
    }
}