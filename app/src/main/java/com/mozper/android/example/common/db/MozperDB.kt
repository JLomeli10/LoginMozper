package com.mozper.android.example.common.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozper.android.example.common.db.dao.UserDataDao
import com.mozper.android.example.common.db.entity.UserData

@Database(
    entities = [UserData::class],
    version = 2,
    exportSchema = true
)

abstract class MozperDB : RoomDatabase() {

    abstract fun userDataDao(): UserDataDao

    companion object {
        private const val DATABASE_NAME = "mozper_db"
        @Volatile
        private var INSTANCE: MozperDB? = null

        fun getInstance(mContext: Context): MozperDB {
            return INSTANCE
                ?: synchronized(this){
                    INSTANCE
                        ?: buildDatabase(mContext).also { INSTANCE = it }
                }
        }

        private fun buildDatabase(mContext: Context): MozperDB{
            return Room.databaseBuilder(
                mContext,
                MozperDB::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}