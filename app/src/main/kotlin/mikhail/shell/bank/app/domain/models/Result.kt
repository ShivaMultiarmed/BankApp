package mikhail.shell.bank.app.domain.models

interface Error

sealed interface Result <out T, out E : Error> {
    data class Success<out T> constructor(val data: T) : Result<T, Nothing>
    data class Failure<out E : Error> constructor(val error: E) : Result<Nothing, E>
}



enum class TransactionError : Error {
    NOT_ENOUGH_MONEY, SENDER_NOT_FOUND, RECEIVER_NOT_FOUND
}
