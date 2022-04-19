package id.jyotisa.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.data.StoryPagingSource
import id.jyotisa.storyapp.database.StoryDatabase
import id.jyotisa.storyapp.model.Story

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getStory(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}