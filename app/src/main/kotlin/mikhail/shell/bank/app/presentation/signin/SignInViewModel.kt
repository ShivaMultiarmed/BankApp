package mikhail.shell.bank.app.presentation.signin

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.domain.usecases.CheckIfSignedIn
import mikhail.shell.bank.app.domain.usecases.SignIn
import mikhail.shell.bank.app.domain.usecases.SignInWithGoogle
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val _checkIfSignedIn: CheckIfSignedIn,
    private val _signIn: SignIn,
    private val _signInWithGoogle: SignInWithGoogle
) : ViewModel() {
    private val _flow = MutableStateFlow(SignInState())
    val stateFlow = _flow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _flow.value
    )
    fun checkIfSignedIn() = _checkIfSignedIn()
    fun signIn(
        email: String,
        password: String
    ) {
        _signIn(
            email,
            password,
            {
                viewModelScope.launch {
                    _flow.emit(SignInState(userid = it))
                }
            },
            {
                viewModelScope.launch {
                    _flow.emit(SignInState(error = it))
                }
            }
        )
    }
    fun signInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential &&
                credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                _signInWithGoogle(googleIdTokenCredential).onSuccess {
                    _flow.value = SignInState(
                        userid = it,
                        error = null
                    )
                }
            }
        }
    }
}