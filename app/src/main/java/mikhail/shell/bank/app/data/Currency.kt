package mikhail.shell.bank.app.data

import androidx.compose.ui.graphics.vector.ImageVector

data class Currency(val name: String, val img: ImageVector, var buy: Double, var sell: Double)
