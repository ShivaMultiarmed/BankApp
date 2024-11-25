package mikhail.shell.bank.app.domain.models

import androidx.compose.ui.graphics.vector.ImageVector

data class Currency(val name: String, val img: ImageVector, var buy: Double, var sell: Double)
