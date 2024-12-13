package mikhail.shell.bank.app.sharedpreferences

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit

@Composable
fun getUserId() = LocalContext.current
    .getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    .getString("userid", null)

@Composable
fun setUserId(userid: String) = LocalContext.current
    .getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    .edit {
        putString("userid", userid)
        apply()
    }

@Composable
fun removeUserId() = LocalContext.current
    .getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    .edit {
        remove("userid")
        apply()
    }