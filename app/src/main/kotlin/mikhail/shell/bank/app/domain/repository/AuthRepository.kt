package mikhail.shell.bank.app.domain.repository

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.errors.AuthError
import mikhail.shell.bank.app.domain.models.Error
import mikhail.shell.bank.app.domain.models.Result

interface AuthRepository {
    fun checkIfSignedIn() : Boolean
    fun signin(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Error) -> Unit
    )
    fun signup(
        email: String,
        password: String,
        user: User,
        onSuccess: (User) -> Unit,
        onFailure: (AuthError) -> Unit
    )
    fun signOut()
    suspend fun signInWithGoogle(
        credential: GoogleIdTokenCredential
    ) : Result<String, AuthError>
}