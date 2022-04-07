package id.jyotisa.storyapp.helper

import android.widget.ImageView
import com.bumptech.glide.Glide
import id.jyotisa.storyapp.R

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_user)
        .into(this)
}