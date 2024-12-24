package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface contactdao {
    @Query("SELECT * FROM contacttable ORDER BY user_name ASC")
    fun shortByName(): Flow<List<contacttable>>
    @Insert
    suspend fun insert(contact: contacttable)
    @Delete
    suspend fun delete(contact: contacttable)
    @Update
    suspend fun update(contact: contacttable)



}