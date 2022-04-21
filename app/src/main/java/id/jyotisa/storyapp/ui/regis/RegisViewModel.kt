package id.jyotisa.storyapp.ui.regis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.model.BaseResponse
import id.jyotisa.storyapp.model.RegisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun postRegis(name: String, email: String, password: String) = authRepository.postRegis(name, email, password)

}