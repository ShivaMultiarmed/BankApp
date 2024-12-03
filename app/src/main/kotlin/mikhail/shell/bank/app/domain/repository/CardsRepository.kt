package mikhail.shell.bank.app.domain.repository

import kotlinx.coroutines.flow.Flow
import mikhail.shell.bank.app.domain.models.Card

interface CardsRepository {
    fun getCards(userid: String): Flow<List<Card>>
    fun createCard(
        card: Card,
        onSuccess: (Long) -> Unit,
        onFailure: (Exception) -> Unit
    )
    suspend fun getCard(cardNumber: Long): Card
    fun updateCard(
        card: Card,
        onSuccess: (Card) -> Unit,
        onFailure: (Exception) -> Unit
    )
    fun deleteCard(
        cardNumber: Long,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )
}