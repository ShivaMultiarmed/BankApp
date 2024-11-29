package mikhail.shell.bank.app.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material.icons.rounded.Cases
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mikhail.shell.bank.app.data.remote.ServiceApi
import mikhail.shell.bank.app.domain.models.FinanceTool
import mikhail.shell.bank.app.domain.repository.ToolsRepository
import javax.inject.Inject

class ToolsRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : ToolsRepository {
    override suspend fun getRecommendedTools(userid: String): Flow<List<FinanceTool>> {
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