package id.jyotisa.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.helper.Utils.wrapEspressoIdlingResource
import id.jyotisa.storyapp.model.BaseResponse
import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.model.RegisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService
) {
    fun postRegis(name: String, email: String, password: String): LiveData<Resource<RegisResponse>> = liveData {
        wrapEspressoIdlingResource {
            try {
                val response = apiService.register(name, email, password)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                Log.d("AuthRepository", "data: ${e.message.toString()} ")
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    fun postLogin(email: String, password: String): LiveData<Resource<LoginResponse>> = liveData {
        wrapEspressoIdlingResource {
            try {
                val response = apiService.login(email, password)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                Log.d("AuthRepository", "data: ${e.message.toString()} ")
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}