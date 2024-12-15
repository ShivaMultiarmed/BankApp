package mikhail.shell.bank.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.domain.errors.ProfileError
import mikhail.shell.bank.app.domain.usecases.GetProfile
import mikhail.shell.bank.app.domain.usecases.SignOut

@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted private val userid: String,
    private val _getProfile: GetProfile,
    private val _signOut: SignOut
) : ViewModel() {
    private val _screenState = MutableStateFlow(ProfileScreenState())
    val screenState = _screenState.catch {
        emit(
            ProfileScreenState(
                user = null,
                error = ProfileError.UNEXPECTED_ERROR,
                isLoading = false
            )
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        _screenState.value
    )

    init {
        loadProfile()
    }

    fun loadProfile() {
        _screenState.value = ProfileScreenState()
        viewModelScope.launch {
            _getProfile(userid)
                .onSuccess {
                    _screenState.value = ProfileScreenState(
                        isLoading = false,
                        error = null,
                        user = it
                    )
                }.onFailure {
                    _screenState.value = ProfileScreenState(
                        isLoading = false,
                        error = it,
                        user = null
                    )
                }
        }
    }

    fun signOut() = _signOut()

    @AssistedFactory
    interface Factory {
        fun create(userid: String): ProfileViewModel
    }
}