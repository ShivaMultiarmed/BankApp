package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : ProfileRepository {

    private val users = db.collection("profiles")

    override suspend fun fetchProfile(userid: String): User {
        val map = users.document(userid).get().await().data
        return map?.let {
            User(
                userid = it["userid"] as String,
                name = it["name"] as String,
                gender = it["gender"] as String
            )
        } ?: User()
    }

    override fun createProfile(
        user: User,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
//        val document = users.document()
//        document.set(user.copy(userid = document.id)).await()
        users.add(user)
            .addOnSuccessListener { docRef ->
                val userid = docRef.id
                users.document(userid)
                    .update("userid", userid)
                    .addOnSuccessListener {
                        onSuccess(userid)
                    }.addOnFailureListener { e ->
                        throw e
                    }
            }.addOnFailureListener {
                onFailure(it)
            }
    }
}