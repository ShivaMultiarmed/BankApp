package mikhail.shell.bank.app.domain.usecases

import mikhail.shell.bank.app.domain.models.Card

class EvaluateBalance {
    operator fun invoke(cards: List<Card>): Double
    {
        return cards.sumOf { card -> card.balance }
    }
}