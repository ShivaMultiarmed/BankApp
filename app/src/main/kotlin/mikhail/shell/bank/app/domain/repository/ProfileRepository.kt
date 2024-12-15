package mikhail.shell.bank.app.domain.repository

import mikhail.shell.bank.app.domain.errors.ProfileError
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.models.Result

interface ProfileRepository {
    suspend fun fetchProfile(userid: String): Result<User, ProfileError>
    fun createProfile(
        user: User,
        onSuccess: (String) -> Unit,
        onFailure: (ProfileError) -> Unit
    )
}