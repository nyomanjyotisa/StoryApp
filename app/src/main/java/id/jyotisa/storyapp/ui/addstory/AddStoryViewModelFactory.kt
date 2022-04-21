package id.jyotisa.storyapp.ui.addstory

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

class AddStoryViewModelFactory(private val storyRepository: StoryRepository, private val pref: UserPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(pref, storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: AddStoryViewModelFactory? = null
        fun getInstance(context: Context, pref: UserPreferences): AddStoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AddStoryViewModelFactory(Injection.provideRepository(context), pref)
            }.also { instance = it }
    }
}