package mikhail.shell.bank.app.domain.repository

import mikhail.shell.bank.app.data.Card

interface CardsRepository {
    suspend fun getCards(): List<Card>
}