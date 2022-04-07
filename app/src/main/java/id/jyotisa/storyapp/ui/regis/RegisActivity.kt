package id.jyotisa.storyapp.ui.regis

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.databinding.ActivityMainBinding
import id.jyotisa.storyapp.databinding.ActivityRegisBinding
import id.jyotisa.storyapp.model.RegisResponse
import id.jyotisa.storyapp.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regis.setOnClickListener { view ->
            postRegis(binding.name.text.toString(),
                binding.email.text.toString(),
                binding.password.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun postRegis(name: String, email: String, password: String) {
        val client = RetrofitConfig.apiInstance.register(name, email, password)
        client.enqueue(object : Callback<RegisResponse> {
            override fun onResponse(
                call: Call<RegisResponse>,
                response: Response<RegisResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Toast.makeText(this@RegisActivity, "Regis Berhasil", Toast.LENGTH_SHORT).show()
                    Intent(this@RegisActivity, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this@RegisActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RegisResponse>, t: Throwable) {
                Toast.makeText(this@RegisActivity, "Gagal", Toast.LENGTH_SHORT).show()
            }
        })
    }
}