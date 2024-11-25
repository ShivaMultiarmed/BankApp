package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.FinanceTool

interface ToolsRepository {
    suspend fun getRecommendedTools(userid: Long): Flow<List<FinanceTool>>
}