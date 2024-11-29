package mikhail.shell.bank.app.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem
import mikhail.shell.bank.app.domain.models.CardType
import mikhail.shell.bank.app.domain.repository.CardsRepository
import javax.inject.Inject

class CardsRepositoryWithFireStore @Inject constructor(
    private val db: FirebaseFirestore
) : CardsRepository {

    private val cards = db.collection("cards")

    override suspend fun getCards(userid: String): Flow<List<Card>> {
        val flow = MutableStateFlow(listOf<Card>())
        cards.whereEqualTo("userid", userid).addSnapshotListener { list, e ->
            val cardsList = list?.map { snapshot -> snapshot.toCard() }?: listOf()
            flow.value = cardsList
        }
        return flow
    }

    override suspend fun createCard(card: Card): Card {
        cards.document(card.number.toString()).set(card).await()
        return card
    }

    override suspend fun getCard(cardNumber: Long): Card {
        return cards.document(cardNumber.toString()).get().await().toCard()
    }

    override suspend fun updateCard(card: Card): Card {
        cards.document(card.number.toString()).set(card).await()
        return card
    }

    override suspend fun deleteCard(cardNumber: Long) {
        cards.document(cardNumber.toString()).delete().await()
    }

    private fun DocumentSnapshot.toCard() : Card
    {
        return data?.let { map ->
            Card(
                userid = map["userid"] as String,
                system = CardSystem.valueOf(map["system"] as String),
                type = CardType.valueOf(map["type"] as String),
                number = map["number"] as Long,
                balance = map["balance"] as Double
            )
        }?: Card()
    }
}