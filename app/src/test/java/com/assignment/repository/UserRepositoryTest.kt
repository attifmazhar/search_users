package com.assignment.repository

import android.arch.lifecycle.MutableLiveData
import com.assignment.data.model.UserDto
import com.assignment.ui.util.SingleLiveEvent
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {


    @InjectMocks
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var userData: MutableLiveData<UserDto>
    @Mock
    lateinit var error: MutableLiveData<String>
    @Mock
    lateinit var showToast: SingleLiveEvent<String>
    @Mock
    lateinit var call: Call<UserDto>

    @Captor
    lateinit var argumentCaptor: ArgumentCaptor<Callback<UserDto>>

    @Mock
    lateinit var callback: Callback<UserDto?>

    //Use this to capture Arguments in Kotlin
    protected fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()


    @Test
    fun getUsersRespone() {

        val serverResponse = Response.success(UserDto(0, false, Arrays.asList()))
        userRepository.searchUsersService("user_name")
        Mockito.verify(call).enqueue(capture(argumentCaptor))
        argumentCaptor.getValue()?.onResponse(call, serverResponse)
        Mockito.verify(callback).onResponse(ArgumentMatchers.any(), ArgumentMatchers.any())
    }

    @Test
    fun getUsersFailure() {

        userRepository.searchUsersService("user_name")
        Mockito.verify(call).enqueue(capture(argumentCaptor))
        argumentCaptor.getValue()?.onFailure(call, Throwable())
        Mockito.verify(callback).onFailure(ArgumentMatchers.any(), ArgumentMatchers.any())
    }

}