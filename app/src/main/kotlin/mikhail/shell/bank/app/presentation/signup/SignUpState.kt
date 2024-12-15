package mikhail.shell.bank.app.presentation.signup

import mikhail.shell.bank.app.domain.errors.AuthError

data class SignUpState(
    val userid: String? = null,
    val error: AuthError? = null
)