package com.example.innobuzztask.domain.usecases

import com.example.innobuzztask.data.mappers.toPostEntity
import com.example.innobuzztask.domain.models.Post
import com.example.innobuzztask.domain.repository.AppRepository
import javax.inject.Inject

class InsertPosts @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(posts: List<Post>) =
        repository.insertPostsToDb(posts.map { it.toPostEntity() })
}