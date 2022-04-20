package id.jyotisa.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.model.LoginResult
import id.jyotisa.storyapp.model.RegisResponse
import id.jyotisa.storyapp.ui.regis.RegisViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    private lateinit var pref: UserPreferences

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(pref, authRepository)
    }

    @Test
    fun `postLogin should be success`() {
        val expectedResponse = MutableLiveData<Resource<LoginResponse>>()
        val loginResultExpected = LoginResult("user-ZmQQkzNMwYceoD0C", "jyotisa", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVptUVFrek5Nd1ljZW9EMEMiLCJpYXQiOjE2NTA0NjEyMjV9.c4KjZ3sMyl9MAOhTi9K5N0ddj4LQkyF1NIENjse0CXU")
        expectedResponse.value = Resource.Success(LoginResponse(false, "success", loginResultExpected))
        Mockito.`when`(loginViewModel.postLogin("jyotisa1@gmail.com", "123123")).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.postLogin("jyotisa1@gmail.com", "123123").getOrAwaitValue()

        Mockito.verify(authRepository).postLogin("jyotisa1@gmail.com", "123123")
        assertNotNull(actualResponse)
        assertTrue(actualResponse is Resource.Success)
    }
}