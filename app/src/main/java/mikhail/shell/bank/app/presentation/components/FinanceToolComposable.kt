package mikhail.shell.bank.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.domain.models.FinanceTool

//@Preview
@Composable
fun FinanceToolComposable(tool: FinanceTool)
{
    Box(
        modifier = Modifier
            .padding(end = 18.dp)
            .width(110.dp)
            .height(110.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Icon(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(tool.color)
                    .padding(4.dp),
                imageVector = tool.imageVector,
                contentDescription = tool.name,
                tint = Color.White
            )
            Text(text = tool.name, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp, lineHeight = 16.sp)
        }
    }
}