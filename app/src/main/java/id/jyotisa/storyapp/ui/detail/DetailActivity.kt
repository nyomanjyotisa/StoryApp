package id.jyotisa.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.jyotisa.storyapp.databinding.ActivityDetailBinding
import id.jyotisa.storyapp.helper.Utils.loadImage
import id.jyotisa.storyapp.model.Story

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)

        val user = intent.getParcelableExtra<Story>(DATA_STORY) as Story
        with(binding) {
            imgStory.loadImage(user.photoUrl)
            username.text = user.name
            desc.text = user.description
            createdAt.text = StringBuilder("Created At : ").append(user.createdAt)
            if (user.lat != null){
                lat.text = StringBuilder("Lat : ").append(user.lat)
            }
            if (user.lon != null){
                lon.text = StringBuilder("Lon : ").append(user.lon)
            }
            showLoading(false)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val DATA_STORY = "data_story"
    }
}