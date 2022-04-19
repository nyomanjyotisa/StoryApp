package id.jyotisa.storyapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.paging.*
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.data.StoryPagingSource
import id.jyotisa.storyapp.data.StoryRemoteMediator
import id.jyotisa.storyapp.database.StoryDatabase
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.model.Story


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val context: Context) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStory(): LiveData<PagingData<Story>> {

        val pref = UserPreferences.getInstance(context.dataStore)

        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllQuote()
            }

        ).liveData
    }
}