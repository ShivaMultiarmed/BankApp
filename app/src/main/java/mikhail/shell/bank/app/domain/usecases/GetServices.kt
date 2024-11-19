package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.ServiceRepository
import javax.inject.Inject

class GetServices @Inject constructor (
    private val serviceRepository: ServiceRepository
) {
    suspend operator fun invoke(userid: Long) = serviceRepository.getRecommendedServices(userid)
}