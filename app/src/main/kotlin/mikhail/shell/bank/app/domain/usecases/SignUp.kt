package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.errors.SignUpError
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
        onFailure: (SignUpError) -> Unit
    ) {
        if (email.isEmpty())
            onFailure(SignUpError.EMAIL_EMPTY)
        else if (password.isEmpty())
            onFailure(SignUpError.PASSWORD_EMPTY)
        else
            authRepository.signup(email, password, user, onSuccess, onFailure)
    }
}