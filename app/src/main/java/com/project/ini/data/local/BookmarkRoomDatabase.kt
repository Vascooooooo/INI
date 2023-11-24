package com.project.ini.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.ini.data.models.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1, exportSchema = true)
abstract class BookmarkRoomDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): BookmarkRoomDatabase {
            if (INSTANCE == null) {
                synchronized(BookmarkRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkRoomDatabase::class.java, "DBInfoTerkini"
                    )
                        .build()
                }
            }
            return INSTANCE as BookmarkRoomDatabase
        }
    }
}