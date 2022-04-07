package id.jyotisa.storyapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import id.jyotisa.storyapp.databinding.ActivityMainBinding
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.ui.login.LoginActivity
import id.jyotisa.storyapp.ui.login.LoginViewModel
import id.jyotisa.storyapp.ui.login.LoginViewModelFactory
import id.jyotisa.storyapp.ui.regis.RegisActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, LoginViewModelFactory(pref))[LoginViewModel::class.java]

        mainViewModel.getAuthToken().observe(this
        ) { authToken: String ->
            if (authToken == "") {
                Intent(this@MainActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

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

        binding.logout.setOnClickListener {
            mainViewModel.saveAuthToken("")
            Intent(this@MainActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}