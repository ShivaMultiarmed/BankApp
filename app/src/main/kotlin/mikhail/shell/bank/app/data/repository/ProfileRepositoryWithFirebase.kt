package mikhail.shell.bank.app.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryWithFirebase @Inject constructor(
    private val db: FirebaseDatabase
) : ProfileRepository {
    private val TAG = "ProfileRepositoryWithFirebase"

    private val users = db.reference.child("users")

    override suspend fun fetchProfile(userid: Long): User {
        return try {
            users
                .child(userid.toString())
                .get()
                .await()
                .getValue(User::class.java)?: throw Exception()
        } catch (e: Exception) {
            Log.e(TAG, e.message?:"ERROR")
            User(userid, e.message?:"default_name_error", "", "")
        }
    }

    override suspend fun createProfile(user: User) {
        val userid = user.userid.toString()
        users
            .child(userid)
            .setValue(user)
            .await()
    }
}