package com.example.alexandrechristie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alexandrechristie.data.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(
        user: User
    )

    @Query("""
        SELECT *
        FROM User
    """)

    fun getUser() : LiveData<List<User>>
    @Query("""
        DELETE
        FROM User
        WHERE id == :id
    """)
    fun deleteUser(id : Int)
}