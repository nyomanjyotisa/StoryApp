package id.jyotisa.storyapp.ui.regis

import androidx.lifecycle.ViewModel
import id.jyotisa.storyapp.data.repository.AuthRepository

class RegisViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun postRegis(name: String, email: String, password: String) = authRepository.postRegis(name, email, password)

}