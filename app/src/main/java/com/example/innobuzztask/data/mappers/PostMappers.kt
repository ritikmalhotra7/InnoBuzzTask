package com.example.innobuzztask.data.mappers

import com.example.innobuzztask.data.local.entities.PostEntity
import com.example.innobuzztask.data.remote.dtos.PostDto
import com.example.innobuzztask.domain.models.Post

fun PostEntity.toPost(): Post {
    return Post(
        userId,
        id,
        title,
        body
    )
}

fun PostDto.toPost(): Post {
    return Post(
        userId,
        id,
        title,
        body
    )
}

fun Post.toPostEntity(): PostEntity {
    return PostEntity(
        userId = userId,
        id = id,
        title = title,
        body = body
    )
}