package id.jyotisa.storyapp.ui.login

import androidx.lifecycle.*
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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