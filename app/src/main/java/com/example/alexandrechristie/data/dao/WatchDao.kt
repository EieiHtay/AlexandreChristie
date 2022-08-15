package com.example.alexandrechristie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.alexandrechristie.data.entity.Watch

@Dao
interface WatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatch(
        watch : Watch

    )

    @Query("""
        SELECT * 
        FROM Watch
        WHERE LOWER(code) LIKE '%' || LOWER(:query) || '%' OR
            LOWER(price) LIKE '%' || LOWER(:query) || '%' OR
            LOWER(category) LIKE '%' || LOWER(:query) || '%'
    """)
    fun searchWatchListing(query: String) : LiveData<List<Watch>>


    @Query("""
        SELECT *
        FROM Watch
        WHERE id == :id
    """)

    fun getWatchById(id : Int) : LiveData<Watch>

    @Query("""
        SELECT *
        FROM Watch
        WHERE category ="Men Collection"
    """)
    fun getWatchByMen():LiveData<List<Watch>>

    @Query("""
        SELECT *
        FROM Watch
        WHERE category ="Women Collection"
    """)
    fun getWatchByWomen():LiveData<List<Watch>>
    @Query("""
        SELECT *
        FROM Watch
        WHERE category ="Pair"
    """)
    fun getWatchByPair():LiveData<List<Watch>>

    @Query("""
        SELECT *
        FROM Watch
        ORDER BY id DESC LIMIT 10
    """)
    fun getLatestWatch():LiveData<List<Watch>>
}