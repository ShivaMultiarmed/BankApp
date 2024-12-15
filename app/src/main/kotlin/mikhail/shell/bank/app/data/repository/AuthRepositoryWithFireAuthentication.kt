package mikhail.shell.bank.app.data.repository

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.errors.AuthError
import mikhail.shell.bank.app.domain.repository.AuthRepository
import mikhail.shell.bank.app.domain.models.Error
import mikhail.shell.bank.app.domain.models.NetworkError
import mikhail.shell.bank.app.domain.models.SignInError
import javax.inject.Inject
import mikhail.shell.bank.app.domain.models.Result

class AuthRepositoryWithFireAuthentication @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRepository {

    private val TAG = "AuthRepositoryWithFireAuthentication"

    override fun checkIfSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun signin(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Error) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userid = it.user?.uid ?: throw Exception()
                onSuccess(userid)
            }.addOnFailureListener {
                val error = when (it) {
                    is FirebaseAuthInvalidCredentialsException -> SignInError.CREDENTIALS_INVALID
                    is FirebaseNetworkException -> NetworkError.NO_CONNECTION
                    else -> SignInError.CREDENTIALS_INVALID
                }
                onFailure(error)
            }
    }

    override fun signup(
        email: String,
        password: String,
        user: User,
        onSuccess: (User) -> Unit,
        onFailure: (AuthError) -> Unit
    ) {
        val users = db.collection("profiles")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userid = it.user?.uid ?: throw Exception()
                val newUser = user.copy(userid = userid)
                users.document(userid)
                    .set(newUser)
                    .addOnSuccessListener {
                        onSuccess(newUser)
                    }.addOnFailureListener {
                        val error = when (it) {
                            else -> AuthError.UNEXPECTED_ERROR
                        }
                        onFailure(error)
                    }
            }.addOnFailureListener {
                val error = when (it) {
                    is FirebaseAuthWeakPasswordException -> AuthError.PASSWORD_INVALID
                    is FirebaseAuthInvalidCredentialsException -> AuthError.MALFORMED_EMAIL
                    is FirebaseAuthUserCollisionException -> AuthError.EMAIL_EXISTS
                    else -> AuthError.UNEXPECTED_ERROR
                }
                onFailure(error)
            }
    }
    override fun signOut() {
        auth.signOut()
    }

    override suspend fun signInWithGoogle(
        credential: GoogleIdTokenCredential
    ) : Result<String, AuthError> {
        val authCredential = GoogleAuthProvider.getCredential(credential.idToken, null)
        try {
            val user = auth.signInWithCredential(authCredential).await().user
            val userid = user?.uid?: throw Exception()
            return Result.Success(userid)
        } catch (e: Exception) {
            return Result.Failure(AuthError.UNEXPECTED_ERROR)
        }
    }
}