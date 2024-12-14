package mikhail.shell.bank.app.data.dto

import java.io.Serializable

data class CurrencyDto(
    val base_code: String = "",
    val conversion_rates: Map<String, Double> = emptyMap()
): Serializable
