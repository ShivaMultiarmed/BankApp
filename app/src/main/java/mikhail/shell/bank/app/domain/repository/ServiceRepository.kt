package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.FinanceTool

interface ServiceRepository {
    suspend fun getRecommendedServices(userid: Long): Flow<List<FinanceTool>>
}