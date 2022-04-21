package id.jyotisa.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import id.jyotisa.storyapp.api.ApiService
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.StoryRemoteMediator
import id.jyotisa.storyapp.database.StoryDatabase
import id.jyotisa.storyapp.helper.Utils
import id.jyotisa.storyapp.helper.Utils.wrapEspressoIdlingResource
import id.jyotisa.storyapp.model.FileUploadResponse
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.model.StoryResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStory(token: String): LiveData<PagingData<Story>> {
        wrapEspressoIdlingResource {
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

    fun getStoryWithLocation(auth_token: String): LiveData<Resource<StoryResponse>> = liveData {
        wrapEspressoIdlingResource {
            try {
                val response = apiService.getStoryMaps(auth_token)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                Log.d("StoryRepository", "data: ${e.message.toString()} ")
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    fun postStories(authToken: String, file: File, description: String, lat: Double?, lon: Double?): LiveData<Resource<FileUploadResponse>> = liveData {
        wrapEspressoIdlingResource {
            try {
                val imageMultipart = Utils.fileToMultipart(file)
                val response = apiService.uploadImage("Bearer $authToken", imageMultipart, description.toRequestBody("text/plain".toMediaType()), lat, lon)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                Log.d("StoryRepository", "data: ${e.message.toString()} ")
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}