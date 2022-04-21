package id.jyotisa.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import id.jyotisa.storyapp.DataDummy
import id.jyotisa.storyapp.MainCoroutineRule
import id.jyotisa.storyapp.data.Resource
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.datastore.UserPreferences
import id.jyotisa.storyapp.getOrAwaitValue
import id.jyotisa.storyapp.model.LoginResponse
import id.jyotisa.storyapp.model.LoginResult
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    private lateinit var pref: UserPreferences
    private val token = DataDummy.token

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(pref, authRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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

    @Test
    fun getAuthToken() = mainCoroutineRule.runBlockingTest {
        val expected = MutableLiveData<String>()
        expected.value = token
        Mockito.`when`(pref.getAuthToken()).thenReturn(expected.asFlow())
        loginViewModel.getAuthToken().getOrAwaitValue()
        Mockito.verify(pref).getAuthToken()
    }

    @Test
    fun saveAuthToken() = mainCoroutineRule.runBlockingTest {
        loginViewModel.saveAuthToken(token)
        Mockito.verify(pref).saveAuthToken(token)
    }
}