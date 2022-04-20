package id.jyotisa.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.model.BaseResponse
import id.jyotisa.storyapp.model.RegisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService
) {
    private val _authInfo = MutableLiveData<Resource<String>>()
    val authInfo: LiveData<Resource<String>> = _authInfo

//    fun postRegis(name: String, email: String, password: String): LiveData<Resource<RegisResponse>> = liveData {
//        emit(Resource.Loading)
//        try {
//            val response = RetrofitConfig.getApiService().register(name, email, password)
//            val articles = response
//            val newsList = articles.map { article ->
//                NewsEntity(
//                    article.title,
//                    article.publishedAt,
//                    article.urlToImage,
//                    article.url
//                )
//            }
//            emit(Result.Success(newsList))
//        } catch (e: Exception) {
//            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
//            emit(Result.Error(e.message.toString()))
//        }
//
//        ////asdasda
//
//        val client = RetrofitConfig.getApiService().register(name, email, password)
//        client.enqueue(object : Callback<RegisResponse> {
//            override fun onResponse(
//                call: Call<RegisResponse>,
//                response: Response<RegisResponse>
//            ) {
//                val responseBody = response.body()
//                if (response.isSuccessful && responseBody != null) {
//                    val message = response.body()?.message.toString()
//                    _authInfo.postValue(Resource.Success(message))
//                }else{
//                    val errorResponse = Gson().fromJson(
//                        response.errorBody()?.charStream(),
//                        BaseResponse::class.java
//                    )
//                    _authInfo.postValue(Resource.Error(errorResponse.message.toString()))
//                }
//            }
//            override fun onFailure(call: Call<RegisResponse>, t: Throwable) {
//                _authInfo.postValue(Resource.Error(t.message.toString()))
//            }
//        })
//    }
}