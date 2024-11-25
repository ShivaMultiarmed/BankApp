package mikhail.shell.bank.app.data.repository

import com.google.firebase.database.FirebaseDatabase
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryWithFirebase @Inject constructor(
    private val db: FirebaseDatabase
) : ProfileRepository {

    private val users = db.reference.child("users")

    override suspend fun fetchProfile(userid: Long): User {
        //users.child(userid.toString()).
        return User(1, "", "", "")
    }

    override suspend fun createProfile(user: User) {
        val userid = user.userid.toString()
        users.child(userid).setValue(user.name)
    }
}