package id.jyotisa.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.databinding.ActivityDetailBinding
import id.jyotisa.storyapp.databinding.ActivityMainBinding
import id.jyotisa.storyapp.helper.loadImage
import id.jyotisa.storyapp.model.Story

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Story>(DATA_STORY) as Story
        Log.v("tes jyo", "woi")
        Log.v(user.description, "woi")
//        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
//        detailViewModel.storyDetail.observe(this) {
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
        }
//            showLoading(false)
//        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val DATA_STORY = "data_story"
    }
}