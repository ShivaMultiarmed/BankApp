package mikhail.shell.bank.app.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.models.FinanceTool
import mikhail.shell.bank.app.domain.usecases.EvaluateBalance
import mikhail.shell.bank.app.domain.usecases.GetCards
import mikhail.shell.bank.app.domain.usecases.GetCurrencies
import mikhail.shell.bank.app.domain.usecases.GetTools

@HiltViewModel(assistedFactory = HomeViewModel.Factory::class)
class HomeViewModel @AssistedInject constructor(
    @Assisted private val userid: String,
    private val getCardsUseCase: GetCards,
    private val evaluateBalanceUseCase: EvaluateBalance,
    private val getToolsUseCase: GetTools,
    private val getCurrenciesUseCase: GetCurrencies
) : ViewModel() {
    private val _cards = MutableStateFlow(DEFAULT_CARDS)

    private val _currencies = MutableStateFlow(DEFAULT_CURRENCIES)

    private val _tools = MutableStateFlow(DEFAULT_TOOLS)

    private var _balance by mutableStateOf(0.0)

    init {
        viewModelScope.launch {
            launch {
                collectFlow(getCardsUseCase(userid), _cards, DEFAULT_CARDS)
            }
            launch {
                collectFlow(getToolsUseCase(userid), _tools, DEFAULT_TOOLS)
            }
            launch {
                collectFlow(getCurrenciesUseCase(), _currencies, DEFAULT_CURRENCIES)
            }
        }
    }

    val screenState = combine(_cards, _currencies, _tools) { cards, currencies, tools ->
            _balance = evaluateBalanceUseCase(cards)
            HomeScreenState(cards, _balance, tools, currencies)
        }
        .catch {
            HomeScreenState(_cards.value, _balance, _tools.value, _currencies.value)
        }
        .asStateFlow(
            initialVal = HomeScreenState(_cards.value, _balance, _tools.value, _currencies.value)
        )

    @AssistedFactory
    interface Factory {
        fun create(userid: String): HomeViewModel
    }

    private companion object {
        val DEFAULT_CARDS = emptyList<Card>()
        val DEFAULT_CURRENCIES = emptyList<Currency>()
        val DEFAULT_TOOLS = emptyList<FinanceTool>()
        const val SUBSCRIPTION_DURATION = 5000L
    }

    fun <T> Flow<T>.asStateFlow(initialVal: T) = this.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(SUBSCRIPTION_DURATION),
        initialVal
    )

    private suspend fun <T> collectFlow(
        useCaseFlow: Flow<T>,
        viewModelFlow: MutableStateFlow<T>,
        defaultValue: T
    ) {
        useCaseFlow.catch {
            viewModelFlow.emit(defaultValue)
        }.collect {
            viewModelFlow.emit(it)
        }
    }
}