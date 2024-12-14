package mikhail.shell.bank.app.domain.errors

import mikhail.shell.bank.app.domain.models.Error

enum class CardError : Error {
    CARD_EXISTS,
    CARD_NOT_EXISTS,
    USER_NOT_EXISTS,
    UNEXPECTED_ERROR,
    PERMISSION_DENIED,
    CANCELLED,
    INPUT_DATA_EXCEPTION
}