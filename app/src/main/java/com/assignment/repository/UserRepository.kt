package com.assignment.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.assignment.data.network.Service
import com.assignment.data.model.UsersDto
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (
        val usersData: MutableLiveData<UsersDto>,
        val error: MutableLiveData<String>
) {

    private val TAG: String = UserRepository::class.java.simpleName

    fun searchUsersService(query: String = "") {

        Service.api.getUser(query).enqueue(object : Callback<UsersDto?> {
            override fun onFailure(call: Call<UsersDto?>?, t: Throwable?) {
                error.postValue("Error happened")
            }
            override fun onResponse(call: Call<UsersDto?>?, response: Response<UsersDto?>?) {

                if (response != null) {
                    if (response.isSuccessful) {
                        usersData.postValue(response.body())

                    } else {
                        error.postValue(response.errorBody()!!.string())
                    }
                }
            }
        })
    }


    fun searchUsersDeferredService(query: String){
        launch {
            try {
                val response = Service.api.getDefer(query).await()
                withContext(UI) {
                    if (response.isSuccessful) {
                        usersData.value = response.body()
                    } else {
                        Log.e("Exception", "${response.errorBody()}")
                        error.value = response.errorBody()?.string()
                    }
                }
            }
            catch (e: Exception) {
                withContext(UI) {
                    Log.e("Exception", e.message)
                    error.value = e.message
                    error.postValue(e.message)
                }
            }
        }
    }


}