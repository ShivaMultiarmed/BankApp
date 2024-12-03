package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.AuthRepository
import javax.inject.Inject

class CheckIfSignedIn @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.checkIfSignedIn()
}