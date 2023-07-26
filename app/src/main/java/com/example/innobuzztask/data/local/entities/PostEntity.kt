package com.example.innobuzztask.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val postId: Int? = null,
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
