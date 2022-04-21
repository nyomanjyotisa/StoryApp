package id.jyotisa.storyapp.ui.regis

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.di.Injection

class RegisViewModelFactory(private val authRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisViewModel::class.java) -> {
                RegisViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: RegisViewModelFactory? = null
        fun getInstance(context: Context): RegisViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: RegisViewModelFactory(Injection.provideAuthRepository(context))
            }.also { instance = it }
    }
}