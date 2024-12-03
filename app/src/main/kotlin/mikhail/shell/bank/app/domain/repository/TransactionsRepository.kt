package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.Result
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.models.TransactionError
import java.time.LocalDateTime

interface TransactionsRepository {
    fun getTransactionsByUserId(
        userid: String,
        from: LocalDateTime = LocalDateTime.MIN,
        to: LocalDateTime = LocalDateTime.now()
    )  : Flow<List<Transaction>>
    suspend fun transferMoney (
        from: Long,
        to: Long,
        amount: Double
    ) : Result<Transaction, TransactionError>
}