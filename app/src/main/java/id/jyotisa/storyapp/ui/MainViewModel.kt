package id.jyotisa.storyapp.ui

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.di.Injection
import id.jyotisa.storyapp.model.Story
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainViewModel(storyRepository: StoryRepository, private val pref: UserPreferences) : ViewModel() {

    private val token = "Bearer " + runBlocking { pref.getAuthToken().first() }

    val story: LiveData<PagingData<Story>> =
        storyRepository.getStory(token).cachedIn(viewModelScope)
}

class ViewModelFactoryMain(private val context: Context, private val pref: UserPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(Injection.provideRepository(context), pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}