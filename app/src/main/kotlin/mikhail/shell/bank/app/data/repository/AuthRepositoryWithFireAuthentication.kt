package mikhail.shell.bank.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryWithFireAuthentication @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository{

    override fun signin(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userid = it.user?.uid?: throw Exception()
                onSuccess(userid)
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    override fun signup(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userid = it.user?.uid?: throw Exception()
                onSuccess(userid)
            }.addOnFailureListener(onFailure)
    }
}