package mikhail.shell.bank.app.presentation.home.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.domain.models.Currency
import mikhail.shell.bank.app.presentation.components.CurrencyRow


@Composable
fun CurrenciesSection(
    currencies: List<Currency>
) {
    var isVisible by remember {
        mutableStateOf(true)
    }
    var openCloseVector by remember {
        mutableStateOf(Icons.Rounded.KeyboardArrowDown)
    }
    val currenciesInteractionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
            .animateContentSize()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = currenciesInteractionSource,
                        indication = null
                    ) {
                        isVisible = !isVisible
                        openCloseVector =
                            if (isVisible)
                                Icons.Rounded.KeyboardArrowDown
                            else
                                Icons.Rounded.KeyboardArrowUp
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ){
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    imageVector = openCloseVector,
                    contentDescription = "Курс валют",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(text = "Курс валют", fontSize = 16.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isVisible) 40.dp else 0.dp)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                val fontSize = 14.sp
                Text(text = "Валюта", fontWeight = FontWeight.Bold, fontSize = fontSize)
                Text(text = "Покупка", fontWeight = FontWeight.Bold, fontSize = fontSize)
                Text(text = "Продажа", fontWeight = FontWeight.Bold, fontSize = fontSize)
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isVisible) 160.dp else 0.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .animateContentSize()
            ) {
                items(currencies) { currency ->
                    CurrencyRow(currency)
                }
            }
        }
    }

}
