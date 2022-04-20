package id.jyotisa.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.databinding.ActivityLoginBinding
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.ui.MainActivity
import id.jyotisa.storyapp.ui.ViewModelFactory
import id.jyotisa.storyapp.ui.regis.RegisActivity
import id.jyotisa.storyapp.ui.regis.RegisViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
    private lateinit var binding: ActivityLoginBinding
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.login.setOnClickListener { view ->
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            when {
                email.isEmpty() -> {
                    binding.email.error = getString(R.string.email_label)
                }
                password.isEmpty() -> {
                    binding.password.error = getString(R.string.password_hint)
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.email.error = getString(R.string.email_not_valid)
                }
                password.length < 6 -> {
                    binding.password.error = getString(R.string.pass_not_valid)
                }
                else -> {
                    showLoading(true)
                    val pref = UserPreferences.getInstance(dataStore)
                    val factory: LoginViewModelFactory = LoginViewModelFactory.getInstance(this, pref)
                    val loginViewModel: LoginViewModel by viewModels {
                        factory
                    }
                    showLoading(true)
                    loginViewModel.postLogin(email, password).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is Resource.Loading -> {
                                    showLoading(true)
                                }
                                is Resource.Success -> {
                                    showLoading(false)
                                    loginViewModel.saveAuthToken("Bearer "+result.data?.loginResult?.token)
                                    startActivity(Intent(this, MainActivity::class.java))
                                }
                                is Resource.Error -> {
                                    showLoading(false)
                                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        binding.regis.setOnClickListener {
            Intent(this@LoginActivity, RegisActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgUnud, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val headerText = ObjectAnimator.ofFloat(binding.headerText, View.ALPHA, 1f).setDuration(500)
        val emailLabel = ObjectAnimator.ofFloat(binding.emailLabel, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.email, View.ALPHA, 1f).setDuration(500)
        val passLabel = ObjectAnimator.ofFloat(binding.passwordLabel, View.ALPHA, 1f).setDuration(500)
        val pass = ObjectAnimator.ofFloat(binding.password, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)
        val regisLabel = ObjectAnimator.ofFloat(binding.regisLabel, View.ALPHA, 1f).setDuration(500)
        val regis = ObjectAnimator.ofFloat(binding.regis, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(headerText, emailLabel, email, passLabel, pass, login, regisLabel, regis)
            startDelay = 500
        }.start()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu2,menu)
        return true

    }override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.locale -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (pressedTime + 4000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(this@LoginActivity, R.string.exit_app, Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }
}