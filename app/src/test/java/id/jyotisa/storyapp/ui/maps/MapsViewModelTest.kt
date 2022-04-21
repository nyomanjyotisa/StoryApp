package id.jyotisa.storyapp.ui.maps

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.jyotisa.storyapp.DataDummy
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.StoryResponse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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