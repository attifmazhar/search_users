package com.assignment.data.model

import com.google.gson.annotations.SerializedName

data class UsersDto(
        val total_count: Int,
        val incomplete_results: Boolean,
    val items: MutableList<User>
)