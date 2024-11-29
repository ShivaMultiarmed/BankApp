package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.Transaction

interface TransactionsRepository {
    fun getTransactionsByUserId(userid: String) : Flow<List<Transaction>>
}