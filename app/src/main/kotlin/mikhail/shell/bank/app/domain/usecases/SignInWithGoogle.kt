package mikhail.shell.bank.app.domain.usecases

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import mikhail.shell.bank.app.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogle @Inject constructor(
    private val _repository: AuthRepository
) {
    suspend operator fun invoke(
        credential: GoogleIdTokenCredential
    ) = _repository.signInWithGoogle(credential)
}