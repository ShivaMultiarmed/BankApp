package mikhail.shell.bank.app.ui.sections.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mikhail.shell.bank.app.data.ToggleableInfo
import mikhail.shell.bank.app.data.User

val user = User(4846L, "John Smith", "qwerty", "Мужчина")

@Preview
@Composable
fun UserDataSection()
{
    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SwitchingTextEdit("Имя", user.name)
        SwitchingTextEdit("Пароль", user.password, true)
        Dropdown(listOf("Мужчина", "Женщина"), user.gender)
    }
}

//@Preview
@Composable
fun TextEdit(
    label: String = "Введите текст",
    text: String = "",
    isEnabled: Boolean = true,
    isSecret: Boolean = false)
{
    var text by remember {
        mutableStateOf(text)
    }
    OutlinedTextField(
        enabled = isEnabled,
        textStyle = TextStyle(fontSize = 16.sp),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        placeholder = {
            Text(
                text = label,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.background(Color.Transparent))
        },
        value = text,
        onValueChange = { text = it },
        visualTransformation = if (!isSecret || isEnabled) VisualTransformation.None
                                else PasswordVisualTransformation()
    )
}
//@Preview
@Composable
fun SwitchingTextEdit(label: String = "Введите текст", text: String = "", isSecret: Boolean = false)
{
    var isEnabled by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextEdit(label, text, isEnabled, isSecret)
        if (isEnabled)
        {
            EditButton(Color(24, 78, 4, 255), Icons.Rounded.Check)
            {
                isEnabled = false
            }
        }
        else
            EditButton(Color(131, 77, 10, 255), Icons.Rounded.Edit)
            {
                isEnabled = true
            }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun Dropdown(genderList: List<String> = emptyList(), value: String = "Гендер")
{
    var selected: String by remember {
        mutableStateOf(value)
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = Modifier
        ) {
            OutlinedTextField(
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                value = selected,
                onValueChange = {},
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = MaterialTheme.colorScheme.secondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(10.dp))

            ){
                genderList.forEach { gender ->
                    DropdownMenuItem(
                        modifier = Modifier,
                        onClick = {
                            isExpanded = false
                            selected = gender
                        },
                        colors = MenuDefaults.itemColors(),
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        text = {
                            Text(
                                modifier = Modifier,
                                text = gender,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }
        }

    }

}
//@Preview
@Composable
fun EditButton(
    color: Color = Color(24, 78, 4, 255),
    imageVector: ImageVector = Icons.Rounded.Check,
    onClick: () -> Unit = {})
{
    Button(
        modifier = Modifier
            .width(40.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            contentDescription = "OK",
            imageVector = imageVector,
            tint = Color.White
        )
    }
}
@Preview
@Composable
fun NotificationsChooserSection()
{
    val options = remember {
        mutableStateListOf(
            ToggleableInfo("Расходы", false),
            ToggleableInfo("Доходы", true)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            "Уведомления:",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp)
        options.forEachIndexed { i, option ->
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        options[i] = options[i].copy(isChecked = !options[i].isChecked)
                    },
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = option.isChecked,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.secondary
                    ),
                    onCheckedChange = {
                        isChecked -> options[i] = options[i].copy(isChecked = isChecked)
                    }
                )
                Text(text = option.title)
            }
        }
    }
}
@Preview
@Composable
fun SyncSwitchSection()
{
    var isSyncEnabled by remember {
        mutableStateOf(true)
    }
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Switch(
            modifier = Modifier,
            checked = isSyncEnabled,
            onCheckedChange = { isChecked -> isSyncEnabled = isChecked },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = MaterialTheme.colorScheme.secondary
            ),
            thumbContent = {
                Icon(
                    imageVector = if (isSyncEnabled) Icons.Rounded.Check else Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        )
        Text(
            "Включить синхронизацию",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )
    }
}
@Preview
@Composable
fun LanguageChooserSection()
{
    val languages = remember {
        mutableStateListOf(
            ToggleableInfo("Русский", true),
            ToggleableInfo("English", false)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            "Язык:",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp)
        languages.forEachIndexed { i, language ->
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.background)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource () },
                        indication = null
                    ) {
                        languages.replaceAll { it.copy(isChecked = false) }
                        languages[i] = languages[i].copy(isChecked = true)
                    },
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = language.isChecked,
                    onClick = {
                        languages.replaceAll { it.copy(isChecked = false) }
                        languages[i] = languages[i].copy(isChecked = true)
                    }
                )
                Text(text = language.title)
            }
        }
    }
}