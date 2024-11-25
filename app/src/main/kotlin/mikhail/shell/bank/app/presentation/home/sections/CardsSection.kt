package mikhail.shell.bank.app.presentation.home.sections

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.R
import mikhail.shell.bank.app.domain.models.Card
import mikhail.shell.bank.app.domain.models.CardSystem.MASTERCARD
import mikhail.shell.bank.app.domain.models.CardSystem.VISA
import mikhail.shell.bank.app.presentation.components.CardComposable

@Preview
@Composable
fun CardsSection(
    cardsList: List<Card> = emptyList()
) {
    if (cardsList.isNotEmpty())
    {
        val context = LocalContext.current
        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(cardsList) { card ->
                CardComposable(
                    card = card,
                    onClick = {
                        Toast.makeText(context, "card number ${card.number}", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}
