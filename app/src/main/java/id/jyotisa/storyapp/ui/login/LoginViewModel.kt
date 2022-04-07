package id.jyotisa.storyapp.ui.login

import androidx.lifecycle.*
import id.jyotisa.storyapp.datastore.UserPreferences
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {
    fun getAuthToken(): LiveData<String> {
        return pref.getAuthToken().asLiveData()
    }

    fun saveAuthToken(authToken: String) {
        viewModelScope.launch {
            pref.saveAuthToken(authToken)
        }
    }
}