package com.purnendu.contactnamechanger.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.purnendu.contactnamechanger.database.daos.AlarmDao
import com.purnendu.contactnamechanger.database.models.Alarm

@Database(
    entities = [Alarm::class],
    version = 1,
    exportSchema = false
)



abstract class AlarmDatabase : RoomDatabase() {

    abstract fun AlarmDao(): AlarmDao

    companion object {

        @Volatile
        private var INSTANCE: AlarmDatabase? = null

        fun getDataBase(context: Context): AlarmDatabase {
            if (INSTANCE == null) {
                synchronized(this)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AlarmDatabase::class.java,
                        "AlarmDatabase"
                    ).build()
                }
            }
            return INSTANCE!!

        }
    }

}