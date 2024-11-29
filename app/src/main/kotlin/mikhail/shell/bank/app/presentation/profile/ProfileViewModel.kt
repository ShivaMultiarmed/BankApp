package mikhail.shell.bank.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mikhail.shell.bank.app.User
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import mikhail.shell.bank.app.domain.usecases.GetProfile

@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted private val userid: String,
    private val getProfile: GetProfile
) : ViewModel() {
    private val _profile = MutableStateFlow<User?>(null)

    init {
        viewModelScope.launch {
            val user = getProfile(userid)
            _profile.emit(user)
        }
    }

    val screenState = combine(_profile) { collectedProfile ->
        val user = collectedProfile[0]
        ProfileScreenState(
            isLoading = false,
            user = user
        )
    }
        .catch {
            emit(
                ProfileScreenState(
                    isLoading = false,
                    user = null
                )
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProfileScreenState()
        )

    @AssistedFactory
    interface Factory {
        fun create(userid: String): ProfileViewModel
    }
}