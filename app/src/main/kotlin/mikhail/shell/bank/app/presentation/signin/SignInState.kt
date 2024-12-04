package mikhail.shell.bank.app.presentation.signin

import mikhail.shell.bank.app.domain.models.Error

data class SignInState(
    val userid: String? = null,
    val error: Error? = null
)
