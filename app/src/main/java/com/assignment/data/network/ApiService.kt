package com.assignment.data.network


import com.assignment.data.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Accept: application/json")
    fun getUser(
            @Query("q") query: String
    ): Call<UserDto>


}