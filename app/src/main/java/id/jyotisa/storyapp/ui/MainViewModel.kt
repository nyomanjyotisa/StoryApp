package id.jyotisa.storyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _stories = MutableLiveData<ArrayList<Story>>()
    val stories: LiveData<ArrayList<Story>> = _stories
    private val toastMessageObserver: MutableLiveData<String?> = MutableLiveData<String?>()

    fun getStories(query: Int) {
        val client = RetrofitConfig.apiInstance.getStories(query)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if (response.isSuccessful) {
                    _stories.postValue(response.body()?.listStory)
                }
            }
            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                toastMessageObserver.value = "onFailure getStories ${t.message}"
            }
        })
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }
}