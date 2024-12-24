package com.example.myapplication.DI
import android.app.Application
import androidx.room.Room
import com.example.myapplication.database.database
import com.example.myapplication.screen.dataseanglescreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object hiltModule {
    @Singleton
    @Provides
    fun databaseProvider(contaxt:Application):database{
        return Room.databaseBuilder(
            contaxt.applicationContext,
            database::class.java,
            "contacttable"
        ).build()

    }

    @Provides
    fun singelscreendataprovider():dataseanglescreen{
        return dataseanglescreen()
    }

}