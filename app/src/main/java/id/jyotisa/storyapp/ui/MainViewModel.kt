package id.jyotisa.storyapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.database.StoryDao
import id.jyotisa.storyapp.database.StoryDatabase
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.model.StoryResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _stories = MutableLiveData<ArrayList<Story>>()
    val stories: LiveData<ArrayList<Story>> = _stories
    private val toastMessageObserver: MutableLiveData<String?> = MutableLiveData<String?>()

    private var storyDao: StoryDao? = null
    private var storyDatabase: StoryDatabase? = StoryDatabase.getDatabase(application)

    init {
        storyDao = storyDatabase?.storyDao()
    }

    fun getStories(auth_token: String) {
        val client = RetrofitConfig.apiInstance.getStories("Bearer $auth_token")
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

    fun saveStoriesToDatabase(listStory: ArrayList<Story>){
        CoroutineScope(Dispatchers.IO).launch {
            storyDao?.delete()
        }
        for (story in listStory.reversed()) {
            CoroutineScope(Dispatchers.IO).launch {
                storyDao?.insert(story)
            }
        }
    }
}