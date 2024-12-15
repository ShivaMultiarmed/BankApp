package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.errors.AuthError
import mikhail.shell.bank.app.domain.repository.AuthRepository
import javax.inject.Inject

class SignUp @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        user: User,
        onSuccess: (User) -> Unit,
        onFailure: (AuthError) -> Unit
    ) {
        if (email.isEmpty())
            onFailure(AuthError.EMAIL_EMPTY)
        else if (password.isEmpty())
            onFailure(AuthError.PASSWORD_EMPTY)
        else
            authRepository.signup(email, password, user, onSuccess, onFailure)
    }
}