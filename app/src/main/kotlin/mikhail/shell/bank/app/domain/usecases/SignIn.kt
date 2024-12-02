package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.AuthRepository
import javax.inject.Inject

class SignIn @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepository.signin(email, password, onSuccess, onFailure)
    }
}