package mikhail.shell.bank.app

import android.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.repository.CardsRepository
import mikhail.shell.bank.app.domain.usecases.GetCards
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

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
            Card("505", CardSystem.MASTERCARD, CardType.BUSINESS, 2432_2342_7654_8776)
        )
        val flow = MutableStateFlow(
            expectedList
        ) as Flow<List<Card>>

        `when`(cardsRepository.getCards(anyString()))
            .thenReturn(flow)
        getCardsUseCase("505L")

    }
}