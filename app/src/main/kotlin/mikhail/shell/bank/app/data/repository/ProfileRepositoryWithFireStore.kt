package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : ProfileRepository{

    private val users = db.collection("profiles")

    override suspend fun fetchProfile(userid: String): User {
        val map = users.document(userid).get().await().data
        return map?.let {
            User(
                userid = it["userid"] as String,
                name = it["name"] as String,
                password = it["password"] as String,
                gender = it["gender"] as String
            )
        }?: User()
    }

    override suspend fun createProfile(user: User) {
        val document = users.document()
        document.set(user.copy(userid = document.id)).await()
    }
}