package id.jyotisa.storyapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.paging.*
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.data.StoryRemoteMediator
import id.jyotisa.storyapp.database.StoryDatabase
import id.jyotisa.storyapp.model.Story


class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStory(token: String): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllQuote()
            }

        ).liveData
    }
}