package mikhail.shell.bank.app.data

data class Card(
    val system: CardSystem,
    val type: CardType,
    val number: String,
    var balance: Double = 0.0
)
enum class CardSystem (val title: String) {
    VISA("Visa"), MASTERCARD("MasterCard")
}
enum class CardType (val purpose: String) {
    BUSINESS("Бизнес"), PENSION("Пенсия"), SAVINGS("Сбережения")
}
