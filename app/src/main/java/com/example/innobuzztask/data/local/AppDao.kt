package com.example.innobuzztask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.innobuzztask.data.local.entities.PostEntity

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(posts: List<PostEntity>)

    @Query("SELECT * FROM post_table")
    suspend fun getAllPost(): List<PostEntity>
}