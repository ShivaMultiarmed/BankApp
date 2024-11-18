package mikhail.shell.bank.app.data.repository

import kotlinx.coroutines.delay
import mikhail.shell.bank.app.data.remote.ProfileApi
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor (
    private val profileApi: ProfileApi
) : ProfileRepository {
    override suspend fun fetchProfile(userid: Long) : User {
        delay(3000)
        return User(505L, "Jordan", "hello", "Мужской")
    }
}