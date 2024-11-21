package mikhail.shell.bank.app.presentation.profile.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mikhail.shell.bank.app.R

@Preview
@Composable
fun AvatarSection(
    modifier: Modifier = Modifier,
    imgSize: Dp = 140.dp
)
{
    Box (
        modifier = modifier
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(imgSize)
                .clip(CircleShape),
            painter = painterResource(R.drawable.businessman),
            contentDescription = "User Profile Image",
            contentScale = ContentScale.Crop
        )
    }
}