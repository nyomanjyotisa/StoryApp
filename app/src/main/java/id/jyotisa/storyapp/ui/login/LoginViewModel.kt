package id.jyotisa.storyapp.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.*
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.ui.MainActivity
import id.jyotisa.storyapp.ui.ViewModelFactory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {
    private var toastMessageObserver: MutableLiveData<String?> = MutableLiveData<String?>()
    private var isSuccessFromVM: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()

    fun postLogin(email: String, password: String) {

        val client = RetrofitConfig.apiInstance.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    saveAuthToken(responseBody.loginResult.token)
                    isSuccessFromVM.value = true
                }else{
                    isSuccessFromVM.value = false
                    if (responseBody != null){
                        toastMessageObserver.value = responseBody.message
                    }else{
                        toastMessageObserver.value = "Email or Password wrong"
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isSuccessFromVM.value = false
                toastMessageObserver.value = "Fail ${t.message}"
            }
        })
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }

    fun getIsSuccess(): LiveData<Boolean?> {
        return isSuccessFromVM
    }

    fun getAuthToken(): LiveData<String> {
        return pref.getAuthToken().asLiveData()
    }

    fun saveAuthToken(authToken: String) {
        viewModelScope.launch {
            pref.saveAuthToken(authToken)
        }
    }
}