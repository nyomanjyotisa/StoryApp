package id.jyotisa.storyapp.ui.maps

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.di.Injection
import id.jyotisa.storyapp.ui.addstory.AddStoryViewModel
import id.jyotisa.storyapp.ui.login.LoginViewModel
import id.jyotisa.storyapp.ui.regis.RegisViewModel

class MapsViewModelFactory(private val application: Application, private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(application, storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: MapsViewModelFactory? = null
        fun getInstance(application: Application, context: Context): MapsViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MapsViewModelFactory(application, Injection.provideRepository(context))
            }.also { instance = it }
    }
}