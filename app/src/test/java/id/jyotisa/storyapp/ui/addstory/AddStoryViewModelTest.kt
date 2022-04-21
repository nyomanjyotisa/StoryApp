package id.jyotisa.storyapp.ui.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import id.jyotisa.storyapp.DataDummy
import id.jyotisa.storyapp.MainCoroutineRule
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.FileUploadResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    @Mock
    private lateinit var pref: UserPreferences
    private val token = DataDummy.token
    @Mock
    private lateinit var file: File

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(pref, storyRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `postStories should be success`() {
        val expectedResponse = MutableLiveData<Resource<FileUploadResponse>>()
        expectedResponse.value = Resource.Success(FileUploadResponse(false, "success"))
        Mockito.`when`(addStoryViewModel.postStories(token, file, "Ini Deskripsi", 1.1, 1.1)).thenReturn(expectedResponse)

        val actualResponse = addStoryViewModel.postStories(token, file, "Ini Deskripsi", 1.1, 1.1).getOrAwaitValue()

        Mockito.verify(storyRepository).postStories(token, file, "Ini Deskripsi", 1.1, 1.1)
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Resource.Success)
    }

    @Test
    fun getAuthToken() = mainCoroutineRule.runBlockingTest {
        val expected = MutableLiveData<String>()
        expected.value = token
        Mockito.`when`(pref.getAuthToken()).thenReturn(expected.asFlow())
        addStoryViewModel.getAuthToken().getOrAwaitValue()
        Mockito.verify(pref).getAuthToken()
    }
}