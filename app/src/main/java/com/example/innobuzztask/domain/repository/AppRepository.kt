package com.example.innobuzztask.domain.repository

import com.example.innobuzztask.data.local.entities.PostEntity
import com.example.innobuzztask.data.remote.dtos.PostDto
import com.example.innobuzztask.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAllPostsFromApi(): Flow<ResponseState<List<PostDto>>>
    suspend fun getAllPostsFromDb(): Flow<ResponseState<List<PostEntity>>>
    suspend fun insertPostsToDb(posts: List<PostEntity>): Flow<ResponseState<Boolean>>
}