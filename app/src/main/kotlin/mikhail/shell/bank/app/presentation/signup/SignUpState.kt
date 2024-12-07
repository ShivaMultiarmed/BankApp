package mikhail.shell.bank.app.presentation.signup

import mikhail.shell.bank.app.domain.errors.SignUpError

data class SignUpState(
    val userid: String? = null,
    val error: SignUpError? = null
)