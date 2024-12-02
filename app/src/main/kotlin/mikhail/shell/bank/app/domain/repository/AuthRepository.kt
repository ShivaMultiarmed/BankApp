package mikhail.shell.bank.app.domain.repository

import mikhail.shell.bank.app.User

interface AuthRepository {
    fun signin(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    )
    fun signup(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    )
}