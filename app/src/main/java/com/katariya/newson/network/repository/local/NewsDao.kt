package com.katariya.newson.network.repository.local

import androidx.room.*
import com.katariya.newson.network.model.Article
import com.katariya.newson.network.model.NewsInfoData

@Dao
interface NewsDao{

    @Query("SELECT * FROM news ORDER BY inserted_time_stamp DESC")
    suspend fun getAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(article: List<Article>)

    @Query("DELETE FROM news")
    fun deleteAll()

    @Update
    fun updateTodo(article: Article)

    @Query("SELECT * FROM news ORDER BY inserted_time_stamp DESC LIMIT 1")
    fun lastInserted(): Article

}