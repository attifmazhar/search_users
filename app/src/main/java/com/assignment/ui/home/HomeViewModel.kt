package com.assignment.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.assignment.data.model.UserDto
import com.assignment.repository.UserRepository

class HomeViewModel(): ViewModel(){

    private val TAG: String = HomeViewModel::class.java.simpleName
    private var userResponse: UserRepository?= null

    val userData: MutableLiveData<UserDto> by lazy { MutableLiveData<UserDto>() }
    val error : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    init {
        userResponse = UserRepository(userData, error)
    }

    fun fetchUsers(query: String = "") {
        userResponse?.searchUsersService(query)
    }

}