package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfile @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(userid: Long) = profileRepository.fetchProfile(userid)
}