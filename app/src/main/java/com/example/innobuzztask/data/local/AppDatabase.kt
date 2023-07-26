package com.example.innobuzztask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.innobuzztask.data.local.entities.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val getDao: AppDao
}