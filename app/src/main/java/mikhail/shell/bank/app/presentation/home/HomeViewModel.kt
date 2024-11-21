package mikhail.shell.bank.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.domain.models.FinanceTool
import mikhail.shell.bank.app.domain.usecases.GetCards
import mikhail.shell.bank.app.domain.usecases.GetCurrencies
import mikhail.shell.bank.app.domain.usecases.GetTools

@HiltViewModel(assistedFactory = HomeViewModel.Factory::class)
class HomeViewModel @AssistedInject constructor(
    @Assisted private val userid: Long,
    private val getCardsUseCase: GetCards,
    private val getToolsUseCase: GetTools,
    private val getCurrenciesUseCase: GetCurrencies
): ViewModel() {
    private val _cards = MutableStateFlow(DEFAULT_CARDS)
    val cards = _cards.asStateFlow()

    private val _currencies = MutableStateFlow(DEFAULT_CURRENCIES)
    val currencies = _currencies.asStateFlow()

    private val _tools = MutableStateFlow(DEFAULT_TOOLS)
    val tools = _tools.asStateFlow()

    init {
        viewModelScope.launch {
            collectFlow(getCardsUseCase(userid), _cards, DEFAULT_CARDS)
        }
        viewModelScope.launch {
            collectFlow(getToolsUseCase(userid), _tools, DEFAULT_TOOLS)
        }
        viewModelScope.launch {
            collectFlow(getCurrenciesUseCase(), _currencies, DEFAULT_CURRENCIES)
        }
    }
    @AssistedFactory
    interface Factory {
        fun create(userid: Long): HomeViewModel
    }
    private companion object {
        val DEFAULT_CARDS = emptyList<Card>()
        val DEFAULT_CURRENCIES = emptyList<Currency>()
        val DEFAULT_TOOLS = emptyList<FinanceTool>()
        const val SUBSCRIPTION_DURATION = 5000L
    }
    fun <T> StateFlow<T>.asStateFlow() = this.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(SUBSCRIPTION_DURATION),
        this.value
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