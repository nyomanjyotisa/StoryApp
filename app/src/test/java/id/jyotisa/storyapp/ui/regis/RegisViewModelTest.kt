package id.jyotisa.storyapp.ui.regis

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.RegisResponse
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito

class RegisViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var regisViewModel: RegisViewModel

    @Before
    fun setUp() {
        regisViewModel = RegisViewModel()
    }

    @Test
    fun `postRegis should be success`() {
        val expectedResponse = MutableLiveData<Resource<String>>()
        expectedResponse.value = Resource.Success("User created")
        `when`(regisViewModel.authInfo).thenReturn(expectedResponse)

        regisViewModel.postRegis("Ini Nama", "iniemail123@gmail.com", "123123")
        val actualResponse = regisViewModel.authInfo.getOrAwaitValue()

        assertNotNull(actualResponse)
        assertTrue(actualResponse is Resource.Success)
    }
}