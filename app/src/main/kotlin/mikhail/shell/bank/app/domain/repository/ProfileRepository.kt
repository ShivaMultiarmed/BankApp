package mikhail.shell.bank.app.domain.repository

import mikhail.shell.bank.app.domain.errors.ProfileError
import mikhail.shell.bank.app.domain.models.User

interface ProfileRepository {
    suspend fun fetchProfile(userid: String): User
    fun createProfile(
        user: User,
        onSuccess: (String) -> Unit,
        onFailure: (ProfileError) -> Unit
    )
}