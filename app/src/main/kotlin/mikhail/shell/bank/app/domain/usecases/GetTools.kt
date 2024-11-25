package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.repository.ToolsRepository
import javax.inject.Inject

class GetTools @Inject constructor (
    private val toolsRepository: ToolsRepository
) {
    suspend operator fun invoke(userid: Long) = toolsRepository.getRecommendedTools(userid)
}