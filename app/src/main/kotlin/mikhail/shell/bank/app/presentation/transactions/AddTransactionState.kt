package mikhail.shell.bank.app.presentation.transactions

import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.models.TransactionError

data class AddTransactionState(
    val transaction: Transaction? = null,
    val error: TransactionError? = null
)