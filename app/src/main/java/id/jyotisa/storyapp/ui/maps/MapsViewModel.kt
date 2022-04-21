package id.jyotisa.storyapp.ui.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.jyotisa.storyapp.data.repository.StoryRepository

class MapsViewModel(application: Application, private val storyRepository: StoryRepository) : AndroidViewModel(application) {

    fun getStoryWithLocation(auth_token: String) = storyRepository.getStoryWithLocation(auth_token)

}