package mikhail.shell.bank.app.presentation.profile

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    suspend fun getProfile(userid: Long) = profileRepository.fetchProfile(userid)
}