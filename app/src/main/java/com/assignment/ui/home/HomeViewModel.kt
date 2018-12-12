package com.assignment.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.assignment.data.model.UsersDto
import com.assignment.repository.UserRepository

class HomeViewModel: ViewModel(){

    private val TAG: String = HomeViewModel::class.java.simpleName
    var userResponse: UserRepository

    val usersData: MutableLiveData<UsersDto> by lazy { MutableLiveData<UsersDto>() }
    val error : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    init {
        userResponse = UserRepository(usersData, error)
    }

    fun fetchUsers(query: String = "") {
        //old retrofit call
//        userResponse.searchUsersService(query)
        userResponse.searchUsersDeferredService(query)

    }



}