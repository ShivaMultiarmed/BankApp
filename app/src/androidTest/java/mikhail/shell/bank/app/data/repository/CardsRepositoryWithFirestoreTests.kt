package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.repository.CardsRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.function.ThrowingRunnable
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

@HiltAndroidTest
class CardsRepositoryWithFirestoreTests {
    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject
    lateinit var db: FirebaseFirestore
    lateinit var repository: CardsRepository

    @Before
    fun initialize() {
        rule.inject()
        repository = CardsRepositoryWithFireStore(db)
    }

    @Test
    fun testCreatingCard() {
        val latch = CountDownLatch(1)
        val card = Card(userid = "mDLOUJm8xZ48xVuhJf9Z")
        repository.createCard(
            card,
            {
                val createdCard = card.copy(number = it)
                latch.countDown()
                Assert.assertNotEquals(0, createdCard.number)
            },
            {
                latch.countDown()
                Assert.assertThrows(Exception::class.java, ThrowingRunnable { })
            }
        )
        latch.await()
    }

    @Test
    fun testUpdatingCard() = runBlocking {
        val latch = CountDownLatch(1)
        val card = repository.getCard(1405649687185712)
        val updatedCard = repository.updateCard(
            card.copy(balance = card.balance + 1000),
            {
                Assert.assertNotEquals(card, it)
                latch.countDown()
            },
            {
                latch.countDown()
            }
        )
        latch.await()
    }

    @Test
    fun testDeletingCard() {
        val latch = CountDownLatch(1)
        repository.deleteCard(
            1405649687185712,
            {
                latch.countDown()
            },
            {
                latch.countDown()
            }
        )
        latch.await()
    }
}