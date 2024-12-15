package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.errors.ProfileError
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject
import mikhail.shell.bank.app.domain.models.Result

class ProfileRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : ProfileRepository {

    private val users = db.collection("profiles")

    override suspend fun fetchProfile(userid: String): Result<User, ProfileError> {
        return try {
            val map = users.document(userid).get().await().data
            Result.Success(User(
                userid = map!!["userid"] as String,
                name = map!!["name"] as String,
                gender = map!!["gender"] as String
            ))
        } catch (e: Exception) {
            Result.Failure(ProfileError.UNEXPECTED_ERROR)
        }
    }

    override fun createProfile(
        user: User,
        onSuccess: (String) -> Unit,
        onFailure: (ProfileError) -> Unit
    ) {
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
            }.addOnFailureListener { exception ->
                val error = when(exception) {
                    else -> ProfileError.UNEXPECTED_ERROR
                }
                onFailure(error)
            }
    }
}