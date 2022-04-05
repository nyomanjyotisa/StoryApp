package id.jyotisa.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.jyotisa.storyapp.databinding.ActivityMainBinding
import id.jyotisa.storyapp.ui.login.LoginActivity
import id.jyotisa.storyapp.ui.regis.RegisActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            Intent(this@MainActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.regis.setOnClickListener {
            Intent(this@MainActivity, RegisActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}