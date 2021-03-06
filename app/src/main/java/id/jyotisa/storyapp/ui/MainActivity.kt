package id.jyotisa.storyapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.jyotisa.storyapp.R
import id.jyotisa.storyapp.adapter.StoryAdapter
import id.jyotisa.storyapp.databinding.ActivityMainBinding
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.ui.addstory.AddStoryActivity
import id.jyotisa.storyapp.ui.detail.DetailActivity
import id.jyotisa.storyapp.ui.login.LoginActivity
import id.jyotisa.storyapp.ui.login.LoginViewModel

class MainActivity : AppCompatActivity(), StoryAdapter.StoryCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val storyAdapter = StoryAdapter(this)
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
    private var pressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = UserPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref))[LoginViewModel::class.java]

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        loginViewModel.getAuthToken().observe(this
        ) { authToken: String ->
            if (authToken == "") {
                Intent(this@MainActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
            mainViewModel.getStories(authToken)
        }

        showLoading(true)

        mainViewModel.stories.observe(this) { listStory ->
            storyAdapter.setData(listStory)
            showLoading(false)
            mainViewModel.saveStoriesToDatabase(listStory)
        }

        mainViewModel.getToastObserver().observe(this) { message ->
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_SHORT
            ).show()
            showNotFound()
            showLoading(false)
        }

        with(binding) {
            rvStories.setHasFixedSize(true)
            rvStories.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStories.adapter = storyAdapter
        }

        binding.fab.setOnClickListener {
            Intent(this@MainActivity, AddStoryActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = UserPreferences.getInstance(dataStore)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(pref))[LoginViewModel::class.java]

        return when(item.itemId){
            R.id.logout -> {
                loginViewModel.saveAuthToken("")
                Intent(this@MainActivity, LoginActivity::class.java).also {
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

    private fun showNotFound() {
        binding.notFound.visibility = View.VISIBLE
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