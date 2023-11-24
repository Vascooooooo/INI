package com.project.ini.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.ini.data.models.ArticlesItem

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteEntity: ArticlesItem)

    @Query("DELETE FROM bookmark WHERE url =:url")
    fun delete(url: String)

    @Query("SELECT * FROM bookmark")
    fun getAllBookmark(): LiveData<List<ArticlesItem>>

    @Query("SELECT * FROM bookmark WHERE url =:url")
    fun checkBookmark(url: String): LiveData<List<ArticlesItem>>
}