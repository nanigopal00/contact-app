package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [contacttable::class], version = 2, exportSchema = true)
 abstract class database:RoomDatabase(){
     abstract fun contactdao():contactdao
}