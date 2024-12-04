package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.AuthRepository
import mikhail.shell.bank.app.domain.models.Error
import javax.inject.Inject

class SignIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Error) -> Unit
    ) {
        authRepository.signin(email, password, onSuccess, onFailure)
    }
}