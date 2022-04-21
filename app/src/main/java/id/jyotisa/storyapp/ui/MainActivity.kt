package id.jyotisa.storyapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Key.VISIBILITY
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.adapter.LoadingStateAdapter
import id.jyotisa.storyapp.adapter.StoryAdapter
import id.jyotisa.storyapp.databinding.ActivityMainBinding
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.ui.addstory.AddStoryActivity
import id.jyotisa.storyapp.ui.detail.DetailActivity
import id.jyotisa.storyapp.ui.login.LoginActivity
import id.jyotisa.storyapp.ui.login.LoginViewModel
import id.jyotisa.storyapp.ui.login.LoginViewModelFactory
import id.jyotisa.storyapp.ui.maps.MapsActivity

class MainActivity : AppCompatActivity(), StoryAdapter.StoryCallback  {
    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStories.layoutManager = LinearLayoutManager(this)

        val pref = UserPreferences.getInstance(dataStore)
        val factory: LoginViewModelFactory = LoginViewModelFactory.getInstance(this, pref)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }

        val mainViewModel: MainViewModel by viewModels {
            ViewModelFactoryMain(this, pref)
        }

        loginViewModel.getAuthToken().observe(this
        ) { authToken: String ->
            if (authToken == "") {
                Intent(this@MainActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
        }

        val adapter = StoryAdapter(this)
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        mainViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
            binding.errorMsg.visibility = GONE
        }

        binding.fab.setOnClickListener {
            Intent(this@MainActivity, AddStoryActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = UserPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(this, pref))[LoginViewModel::class.java]

        return when(item.itemId){
            R.id.logout -> {
                loginViewModel.saveAuthToken("")
                Intent(this@MainActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }
            R.id.map -> {
                Intent(this@MainActivity, MapsActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }
            R.id.locale -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStoryClick(story: Story) {
        val storyDetailIntent = Intent(this, DetailActivity::class.java)
        storyDetailIntent.putExtra(DetailActivity.DATA_STORY, story)
        startActivity(storyDetailIntent)
    }

    override fun onBackPressed() {
        if (pressedTime + 4000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(this@MainActivity, R.string.exit_app, Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }
}