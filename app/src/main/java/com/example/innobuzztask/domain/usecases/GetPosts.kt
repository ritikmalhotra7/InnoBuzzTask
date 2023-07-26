package com.example.innobuzztask.domain.usecases

import com.example.innobuzztask.data.local.entities.PostEntity
import com.example.innobuzztask.domain.repository.AppRepository
import com.example.innobuzztask.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Flow<ResponseState<List<PostEntity>>> {
        return repository.getAllPostsFromDb()
    }
}