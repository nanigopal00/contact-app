package com.example.myapplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class contacttable(
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @ColumnInfo(name = "user_name") var name: String,
    @ColumnInfo(name = "user_number") var number: String,
    @ColumnInfo(name = "user_email") var email: String,
    @ColumnInfo(name = "user_image") var image:ByteArray

)
