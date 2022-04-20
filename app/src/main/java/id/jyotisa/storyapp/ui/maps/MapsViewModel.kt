package id.jyotisa.storyapp.ui.maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(application: Application, private val storyRepository: StoryRepository) : AndroidViewModel(application) {

    fun getStoryWithLocation(auth_token: String) = storyRepository.getStoryWithLocation(auth_token)

}