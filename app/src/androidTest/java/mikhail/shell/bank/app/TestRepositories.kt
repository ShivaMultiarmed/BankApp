package mikhail.shell.bank.app

import android.util.Log
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import mikhail.shell.bank.app.domain.repository.CardsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class TestRepositories {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    @Inject
    lateinit var cardsRepository: CardsRepository
    @Before
    fun enable() {
        hiltRule.inject()
    }
    @Test
    fun testCardRepository() = runBlocking {
        cardsRepository.getCards(505).collect { cards ->
            cards.forEach { card ->
                Log.i("Fake Repository", card.type.name)
            }
        }
    }
}