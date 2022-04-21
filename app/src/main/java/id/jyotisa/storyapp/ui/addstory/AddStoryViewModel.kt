package id.jyotisa.storyapp.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import java.io.File

class AddStoryViewModel(private val pref: UserPreferences, private val storyRepository: StoryRepository) : ViewModel() {

    fun getAuthToken(): LiveData<String> {
        return pref.getAuthToken().asLiveData()
    }

    fun postStories(authToken: String, file: File, description: String, lat: Double?, lon: Double?) = storyRepository.postStories(authToken, file, description, lat, lon)

}