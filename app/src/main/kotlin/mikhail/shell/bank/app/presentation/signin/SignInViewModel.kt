package mikhail.shell.bank.app.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.domain.models.NetworkError
import mikhail.shell.bank.app.domain.models.SignInError
import mikhail.shell.bank.app.domain.usecases.CheckIfSignedIn
import mikhail.shell.bank.app.domain.usecases.SignIn
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val checkIfSignedInUseCase: CheckIfSignedIn,
    private val signInUseCase: SignIn
) : ViewModel() {
    private val _flow = MutableStateFlow(SignInState())
    val stateFlow = _flow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _flow.value
    )
    fun checkIfSignedIn() = checkIfSignedInUseCase()
    fun signIn(email: String, password: String) {
        signInUseCase(
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
}