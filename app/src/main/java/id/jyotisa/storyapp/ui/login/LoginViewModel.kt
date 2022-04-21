package id.jyotisa.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreferences, private val authRepository: AuthRepository) : ViewModel() {

    fun postLogin(email: String, password: String) = authRepository.postLogin(email, password)

    fun getAuthToken(): LiveData<String> {
        return pref.getAuthToken().asLiveData()
    }

    fun saveAuthToken(authToken: String) {
        viewModelScope.launch {
            pref.saveAuthToken(authToken)
        }
    }
}