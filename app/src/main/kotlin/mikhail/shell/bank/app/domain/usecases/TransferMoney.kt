package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.models.Result
import mikhail.shell.bank.app.domain.models.Transaction
import mikhail.shell.bank.app.domain.models.TransactionError
import mikhail.shell.bank.app.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransferMoney @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {
    suspend operator fun invoke(
        from: Long,
        to: Long,
        amount: Double
    ): Result<Transaction, TransactionError> {
        if (from == 0L)
            return Result.Failure(TransactionError.SENDER_NOT_SPECIFIED)
        if (to == 0L)
            return Result.Failure(TransactionError.RECEIVER_NOT_SPECIFIED)
        if (amount == 0.0)
            return Result.Failure(TransactionError.AMOUNT_NOT_SPECIFIED)
        if (amount < 0.0)
            return Result.Failure(TransactionError.AMOUNT_LESS_THAT_ZERO)
        return transactionsRepository.transferMoney(from, to, amount)
    }
}