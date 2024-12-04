package mikhail.shell.bank.app.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.domain.usecases.TransferMoney
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transferMoney: TransferMoney
) : ViewModel() {
    private val _state = MutableStateFlow(AddTransactionState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )
    fun transferMoney(from:Long, to:Long, amount: Double)
    {
        viewModelScope.launch {
            val result = transferMoney.invoke(from, to, amount)
            result.onSuccess { transaction ->
                _state.update {
                    it.copy(
                        transaction = transaction,
                        error = null
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        transaction = null,
                        error = error
                    )
                }
            }
        }
    }
}