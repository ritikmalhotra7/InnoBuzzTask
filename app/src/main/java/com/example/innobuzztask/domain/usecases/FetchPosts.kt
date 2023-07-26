package com.example.innobuzztask.domain.usecases

import com.example.innobuzztask.data.remote.dtos.PostDto
import com.example.innobuzztask.domain.repository.AppRepository
import com.example.innobuzztask.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPosts @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): Flow<ResponseState<List<PostDto>>> {
        return repository.getAllPostsFromApi()
    }
}