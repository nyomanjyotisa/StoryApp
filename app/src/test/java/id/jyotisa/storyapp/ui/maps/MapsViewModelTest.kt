package id.jyotisa.storyapp.ui.maps

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import id.jyotisa.storyapp.DataDummy
import id.jyotisa.storyapp.MainCoroutineRule
import id.jyotisa.storyapp.adapter.StoryAdapter
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.model.LoginResult
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.model.StoryResponse
import id.jyotisa.storyapp.ui.MainViewModel
import id.jyotisa.storyapp.ui.PagedTestDataSources
import id.jyotisa.storyapp.ui.login.LoginViewModel
import id.jyotisa.storyapp.ui.noopListUpdateCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    @Mock
    private lateinit var application: Application
    private val dummyNews = DataDummy.generateDummyStoryResponse()
    private val token = DataDummy.token

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(application, storyRepository)
    }

    @Test
    fun `getStoryWithLocation should be success`() {
        val expectedResponse = MutableLiveData<Resource<StoryResponse>>()
        expectedResponse.value = Resource.Success(StoryResponse(false, "success", dummyNews))
        Mockito.`when`(mapsViewModel.getStoryWithLocation(token)).thenReturn(expectedResponse)

        val actualResponse = mapsViewModel.getStoryWithLocation(token).getOrAwaitValue()

        Mockito.verify(storyRepository).getStoryWithLocation(token)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Resource.Success)
    }
}