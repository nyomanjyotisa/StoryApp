package id.jyotisa.storyapp.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    @PrimaryKey
    val id: String?,
    val name: String?,
    val description: String?,
    val photoUrl: String?,
    val createdAt: String?,
    val lat: Double?,
    val lon: Double?
) : Parcelable