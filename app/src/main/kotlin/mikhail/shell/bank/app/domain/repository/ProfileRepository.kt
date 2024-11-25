package mikhail.shell.bank.app.domain.repository

import mikhail.shell.bank.app.User

interface ProfileRepository {
    suspend fun fetchProfile(userid: Long): User
    suspend fun createProfile(user: User)
}