package com.example.innobuzztask.data.remote.dtos

data class PostDto(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)