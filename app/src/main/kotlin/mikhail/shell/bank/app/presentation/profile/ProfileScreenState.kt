package mikhail.shell.bank.app.presentation.profile

import mikhail.shell.bank.app.User

data class ProfileScreenState(
    val isLoading: Boolean = true,
    val user: User? = null
)
