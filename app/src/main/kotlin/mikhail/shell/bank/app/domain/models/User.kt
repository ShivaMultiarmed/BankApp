package mikhail.shell.bank.app.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class User(
    val userid: String? = null,
    val name: String = "",
    val gender: String = ""
) : Parcelable