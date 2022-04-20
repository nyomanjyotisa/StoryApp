package id.jyotisa.storyapp.ui.regis

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.RegisResponse
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var regisViewModel: RegisViewModel

    @Before
    fun setUp() {
        regisViewModel = RegisViewModel(authRepository)
    }

    @Test
    fun `postRegis should be success`() {
        val expectedResponse = MutableLiveData<Resource<RegisResponse>>()
        expectedResponse.value = Resource.Success(RegisResponse(false, "User Created"))
        `when`(regisViewModel.postRegis("tes", "inmsdfsdf@gmail.com", "123123")).thenReturn(expectedResponse)

        val actualResponse = regisViewModel.postRegis("tes", "inmsdfsdf@gmail.com", "123123").getOrAwaitValue()

        Mockito.verify(authRepository).postRegis("tes", "inmsdfsdf@gmail.com", "123123")
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Resource.Success)
    }
}