package mikhail.shell.bank.app.domain.errors

import mikhail.shell.bank.app.domain.models.Error

enum class AuthError : Error {
    EMAIL_EXISTS, EMAIL_EMPTY, PASSWORD_EMPTY, PASSWORD_INVALID, UNEXPECTED_ERROR, MALFORMED_EMAIL
}