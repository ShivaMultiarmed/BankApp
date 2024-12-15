package mikhail.shell.bank.app.presentation.profile

import mikhail.shell.bank.app.domain.errors.ProfileError
import mikhail.shell.bank.app.domain.models.User

data class ProfileScreenState(
    val isLoading: Boolean = true,
    val error: ProfileError? = null,
    val user: User? = null
)
