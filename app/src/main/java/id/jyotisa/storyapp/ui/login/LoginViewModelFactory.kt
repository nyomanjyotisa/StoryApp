package id.jyotisa.storyapp.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.di.Injection
import id.jyotisa.storyapp.ui.addstory.AddStoryViewModel
import id.jyotisa.storyapp.ui.login.LoginViewModel
import id.jyotisa.storyapp.ui.regis.RegisViewModel

class LoginViewModelFactory(private val authRepository: AuthRepository, private val pref: UserPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref, authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: LoginViewModelFactory? = null
        fun getInstance(context: Context, pref: UserPreferences): LoginViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: LoginViewModelFactory(Injection.provideAuthRepository(context), pref)
            }.also { instance = it }
    }
}