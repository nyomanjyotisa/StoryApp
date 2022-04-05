package id.jyotisa.storyapp.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.databinding.ActivityLoginBinding
import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.model.RegisResponse
import id.jyotisa.storyapp.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener { view ->
            postLogin(binding.email.text.toString(),
                binding.password.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun postLogin(email: String, password: String) {
        val client = RetrofitConfig.apiInstance.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginActivity, MainActivity::class.java).also {
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }
}