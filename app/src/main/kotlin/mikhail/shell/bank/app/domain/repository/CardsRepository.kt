package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.Card

interface CardsRepository {
    suspend fun getCards(userid: Long): Flow<List<Card>>
    suspend fun createCard(userid: Long, card: Card): Card
    suspend fun getCard(cardNumber: Long): Card
    suspend fun updateCard(card: Card): Card
    suspend fun deleteCard(cardNumber: Long)
}