package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.Card

interface CardsRepository {
    suspend fun getCards(userid: Long): Flow<List<Card>>
}