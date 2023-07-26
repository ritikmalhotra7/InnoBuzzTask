package com.example.innobuzztask.data.remote

import com.example.innobuzztask.data.remote.dtos.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<PostDto>>
}