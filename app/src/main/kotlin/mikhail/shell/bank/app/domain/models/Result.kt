package mikhail.shell.bank.app.domain.models

interface Error

sealed class Result <out T, out E : Error> {
    data class Success<out T> constructor(val data: T) : Result<T, Nothing>()
    data class Failure<out E : Error> constructor(val error: E) : Result<Nothing, E>()

    fun onSuccess(action: (T) -> Unit) : Result<T, E> {
        return when (this)
        {
            is Success -> {
                action(data)
                this
            }
            is Failure -> this
        }
    }
    fun onFailure(action: (E) -> Unit): Result<T, E>
    {
        return when(this)
        {
            is Success -> this
            is Failure -> {
                action(error)
                this
            }
        }
    }
}



enum class TransactionError : Error {
    NOT_ENOUGH_MONEY,
    SENDER_NOT_FOUND,
    RECEIVER_NOT_FOUND,
    SENDER_NOT_SPECIFIED,
    RECEIVER_NOT_SPECIFIED,
    AMOUNT_NOT_SPECIFIED,
    AMOUNT_LESS_THAT_ZERO
}

enum class SignInError : Error {
    CREDENTIALS_INVALID
}

enum class NetworkError : Error {
    NO_CONNECTION
}
