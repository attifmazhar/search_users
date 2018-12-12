package com.assignment.ui.home

import android.arch.lifecycle.MutableLiveData
import com.assignment.data.model.UsersDto
import com.assignment.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @InjectMocks
    lateinit var homeViewModel: HomeViewModel
    @Mock
    lateinit var userResponse: UserRepository
    @Mock
    lateinit var usersData: MutableLiveData<UsersDto>
    @Mock
    lateinit var error : MutableLiveData<String>

    @Test
    fun fetchUsers() {
        val query = "user_name"
        homeViewModel.fetchUsers(query)
        Mockito.verify(userResponse).searchUsersService(query)
    }
}