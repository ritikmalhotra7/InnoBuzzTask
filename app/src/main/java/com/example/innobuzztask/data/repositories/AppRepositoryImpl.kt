package com.example.innobuzztask.data.repositories

import com.example.innobuzztask.data.local.AppDao
import com.example.innobuzztask.data.local.entities.PostEntity
import com.example.innobuzztask.data.remote.AppApi
import com.example.innobuzztask.data.remote.dtos.PostDto
import com.example.innobuzztask.domain.repository.AppRepository
import com.example.innobuzztask.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val api: AppApi,
    private val dbDao: AppDao
) : AppRepository {
    override suspend fun getAllPostsFromApi(): Flow<ResponseState<List<PostDto>>> = flow {
        emit(ResponseState.Loading())
        val data = api.getPosts()
        try {
            if (data.isSuccessful) {
                emit(ResponseState.Success(data.body()!!))
            } else {
                emit(ResponseState.Error(data.message()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.toString()))
        }
    }

    override suspend fun getAllPostsFromDb(): Flow<ResponseState<List<PostEntity>>> = flow {
        emit(ResponseState.Loading())
        try {
            val data = dbDao.getAllPost()
            emit(ResponseState.Success(data))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.toString()))
        }
    }

    override suspend fun insertPostsToDb(posts: List<PostEntity>): Flow<ResponseState<Boolean>> =
        flow {
            try {
                dbDao.insertPost(posts)
                emit(ResponseState.Success(true))
            } catch (e: Exception) {
                emit(ResponseState.Error(e.toString()))
            }
        }
}