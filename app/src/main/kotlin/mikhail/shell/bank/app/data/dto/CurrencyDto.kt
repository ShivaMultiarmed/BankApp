package mikhail.shell.bank.app.data.dto

data class CurrencyDto(
    val base_code: String = "",
    val conversion_rates: Map<String, Double> = emptyMap()
)
