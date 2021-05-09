package com.tistory.realapril.mybooks.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tistory.realapril.mybooks.entity.Item

@androidx.room.Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun bookDao() : BookDao

    companion object{

        @Volatile
        private var INSTANCE : Database? = null

        fun getDatabase(context: Context) : Database {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    "book_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }

        }

    }

}