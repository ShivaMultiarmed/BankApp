package mikhail.shell.bank.app.data.repository

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.AuthRepository
import mikhail.shell.bank.app.domain.models.Error
import mikhail.shell.bank.app.domain.models.NetworkError
import mikhail.shell.bank.app.domain.models.SignInError
import javax.inject.Inject

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
        onFailure: (Exception) -> Unit
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
                    }.addOnFailureListener(onFailure)
            }.addOnFailureListener(onFailure)
    }

    override fun signOut() {
        auth.signOut()
    }
}