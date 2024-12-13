package mikhail.shell.bank.app.sharedpreferences

import android.content.Context
import androidx.core.content.edit

fun Context.getUserId() = getSharedPreferences("auth_details", Context.MODE_PRIVATE)
            .getString("userid", null)

fun Context.setUserId(userid: String) = getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    .edit {
        putString("userid", userid)
        apply()
    }

fun Context.removeUserId() = getSharedPreferences("auth_details", Context.MODE_PRIVATE)
    .edit {
        remove("userid")
        apply()
    }