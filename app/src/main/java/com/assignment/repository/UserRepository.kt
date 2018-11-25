package com.assignment.repository

import android.arch.lifecycle.MutableLiveData
import com.assignment.data.network.Service
import com.assignment.data.model.UserDto
import com.assignment.ui.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository (
        val userData: MutableLiveData<UserDto>,
        val error: MutableLiveData<String>
) {

    private val TAG: String = UserRepository::class.java.simpleName
    private var lastUpdatedTime: Long = 0L
    private var apiTries: Int = 0

    private fun checkApiTriesInOneMinute(): Boolean {

        if (lastUpdatedTime + 60000 < System.currentTimeMillis()) {
            lastUpdatedTime = System.currentTimeMillis()
            apiTries = 0
        }
        return checkApiTimeWithInOneMinute() && apiTries < 10
    }

    private fun checkApiTimeWithInOneMinute(): Boolean {
        return lastUpdatedTime + 60000 > System.currentTimeMillis()
    }

    fun searchUsersService(query: String = "") {

        if (checkApiTriesInOneMinute()) {
            apiTries++
            Service.api!!.getUser(query).enqueue(object : Callback<UserDto?> {
                override fun onFailure(call: Call<UserDto?>?, t: Throwable?) {
                    error.postValue("Error happened")
                }
                override fun onResponse(call: Call<UserDto?>?, response: Response<UserDto?>?) {

                    if (response != null) {
                        if (response.isSuccessful) {
                            userData.postValue(response.body())

                        } else {
                            error.postValue(response.errorBody()!!.string())
                        }
                    }
                }
            })
        } else {
            error.value = "Reached limit for 10 API calls in 1 minute"
        }
    }


}