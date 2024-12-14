package mikhail.shell.bank.app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.domain.models.Currency

@Composable
fun CurrencyRow(
    currency: Currency
) {
    val colWidth = 60.dp
    val fontSize = 14.sp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    )
    {
        Box(modifier = Modifier.width(colWidth), contentAlignment = Alignment.Center)
        {
            Text(text = currency.name, fontSize = fontSize)
        }
        Box(modifier = Modifier.width(colWidth), contentAlignment = Alignment.Center)
        {
            Text(text = "${currency.buy.toDisplayableBalance()} ₽", fontSize = fontSize)
        }
        Box(modifier = Modifier.width(colWidth), contentAlignment = Alignment.Center)
        {
            Text(text = "${currency.sell.toDisplayableBalance()} ₽", fontSize = fontSize)
        }
    }
}