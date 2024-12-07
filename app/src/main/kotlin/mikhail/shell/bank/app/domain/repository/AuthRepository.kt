package mikhail.shell.bank.app.domain.repository

import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.errors.SignUpError
import mikhail.shell.bank.app.domain.models.Error

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
        onFailure: (SignUpError) -> Unit
    )
    fun signOut()
}