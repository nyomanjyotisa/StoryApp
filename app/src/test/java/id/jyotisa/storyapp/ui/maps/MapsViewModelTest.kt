package id.jyotisa.storyapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import id.jyotisa.storyapp.DataDummy
import id.jyotisa.storyapp.MainCoroutineRule
import id.jyotisa.storyapp.adapter.StoryAdapter
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.Story
import id.jyotisa.storyapp.ui.MainViewModel
import id.jyotisa.storyapp.ui.PagedTestDataSources
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()

    @Mock
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyNews = DataDummy.generateDummyStoryResponse()

    @Test
    fun `when Network Error Should Return Error`() {
//        val expectedStories = MutableLiveData<List<String>>()
//        expectedStories.value = Result.Error("Error")
//        Mockito.`when`(newsViewModel.getHeadlineNews()).thenReturn(headlineNews)
//
//        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
//        Mockito.verify(newsRepository).getHeadlineNews()
//        Assert.assertNotNull(actualNews)
//        Assert.assertTrue(actualNews is Result.Error)
    }
}