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

class RegisViewModel(): ViewModel() {
    private val _authInfo = MutableLiveData<Resource<String>>()
    val authInfo: LiveData<Resource<String>> = _authInfo

    fun postRegis(name: String, email: String, password: String) {
        val client = RetrofitConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisResponse> {
            override fun onResponse(
                call: Call<RegisResponse>,
                response: Response<RegisResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val message = response.body()?.message.toString()
                    _authInfo.postValue(Resource.Success(message))
                }else{
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        BaseResponse::class.java
                    )
                    _authInfo.postValue(Resource.Error(errorResponse.message.toString()))
                }
            }
            override fun onFailure(call: Call<RegisResponse>, t: Throwable) {
                _authInfo.postValue(Resource.Error(t.message.toString()))
            }
        })
    }
}