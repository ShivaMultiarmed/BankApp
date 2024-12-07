package mikhail.shell.bank.app.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import mikhail.shell.bank.app.domain.models.User
import mikhail.shell.bank.app.domain.usecases.SignUp
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val _signUp: SignUp
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)
    fun signUp(
        email: String,
        password: String,
        user: User
    ) {
        _signUp(
            email,
            password,
            user,
            {user ->
                _state.update {
                    it.copy(
                        userid = user.userid,
                        error = null
                    )
                }
            },
            { e ->
                _state.update {
                    it.copy(
                        userid = null,
                        error = e
                    )
                }
            }
        )
    }
}