package mikhail.shell.bank.app.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material.icons.rounded.Cases
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import mikhail.shell.bank.app.data.remote.ServiceApi
import mikhail.shell.bank.app.domain.FinanceTool
import mikhail.shell.bank.app.domain.repository.ServiceRepository
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : ServiceRepository {
    override suspend fun getRecommendedServices(userid: Long): Flow<List<FinanceTool>> {
        delay(1000)
        val toolsList = listOf(
            FinanceTool("Анализ расходов", Color(18, 99, 197, 255), Icons.Rounded.Analytics),
            FinanceTool("Инвестиции", Color(122, 197, 18, 255), Icons.Rounded.Cases),
            FinanceTool("Новости", Color(81, 18, 197, 255), Icons.Rounded.Newspaper),
            FinanceTool("Кэшбэк", Color(81, 18, 197, 255), Icons.Rounded.Backspace)
        )
        return flow {
            emit(toolsList)
        }
    }
}