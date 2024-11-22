package mikhail.shell.bank.app

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.util.Log
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.usecases.GetCards
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import javax.inject.Inject

@HiltAndroidTest
class TestUseCases {
    //    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    //@Inject
    private val cardsRepository = mock(CardsRepository::class.java)
    private val getCardsUseCase by lazy { GetCards(cardsRepository) }

    //    @Before
//    fun enable() {
//        hiltRule.inject()
//    }
    @Test
    fun testGetCardsUseCase_Success() = runTest {
//        cardsRepository.getCards(505).collect { cards ->
//            cards.forEach { card ->
//                Log.i("Fake Repository", card.type.name)
//            }
//        }
        val expectedList = listOf(
            Card(CardSystem.MASTERCARD, CardType.BUSINESS, "2432 2342 7654 8776")
        )
        val expectedFlow = MutableStateFlow(
            expectedList
        ) as Flow<List<Card>>

        `when`(cardsRepository.getCards(anyLong()))
            .thenReturn(expectedFlow)
        getCardsUseCase(505L)

    }
}