package id.jyotisa.storyapp.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
) : Parcelable