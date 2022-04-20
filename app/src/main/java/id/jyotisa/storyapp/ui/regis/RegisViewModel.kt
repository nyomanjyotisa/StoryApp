package id.jyotisa.storyapp.ui.regis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.model.RegisResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisViewModel: ViewModel() {
    private var toastMessageObserver: MutableLiveData<String?> = MutableLiveData<String?>()
    private var isSuccessFromVM: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()

    fun postRegis(name: String, email: String, password: String) {
        val client = RetrofitConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisResponse> {
            override fun onResponse(
                call: Call<RegisResponse>,
                response: Response<RegisResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    toastMessageObserver.value = "Registration Success. Please Login"
                    isSuccessFromVM.value = true
                }else{
                    isSuccessFromVM.value = false
                    if (responseBody != null) {
                        toastMessageObserver.value = responseBody.message
                    }else{
                        toastMessageObserver.value = "Email already used. Please use another email."
                    }
                }
            }
            override fun onFailure(call: Call<RegisResponse>, t: Throwable) {
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
}