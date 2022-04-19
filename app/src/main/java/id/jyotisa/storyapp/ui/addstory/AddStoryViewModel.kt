package id.jyotisa.storyapp.ui.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.model.FileUploadResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel(private val pref: UserPreferences) : ViewModel() {
    private var toastMessageObserver: MutableLiveData<String?> = MutableLiveData<String?>()
    private var isSuccessFromVM: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()


    fun getAuthToken(): LiveData<String> {
        return pref.getAuthToken().asLiveData()
    }

    fun getStories(authToken: String, imageMultipart: MultipartBody.Part, description: String) {
        val service = RetrofitConfig.getApiService().uploadImage("Bearer $authToken", imageMultipart, description.toRequestBody("text/plain".toMediaType()))

        service.enqueue(object : Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        toastMessageObserver.value = responseBody.message
                        isSuccessFromVM.value = true
                    }
                } else {
                    isSuccessFromVM.value = false
                    toastMessageObserver.value = "onFailure add story ${response.message()}"
                }
            }
            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                isSuccessFromVM.value = false
                toastMessageObserver.value = "onFailure add story ${t.message}"
            }
        })
    }

    fun getToastObserver(): LiveData<String?> {
        return toastMessageObserver
    }

    fun getIsSuccess(): LiveData<Boolean?> {
        return isSuccessFromVM
    }
}