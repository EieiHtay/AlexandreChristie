package com.example.alexandrechristie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alexandrechristie.data.entity.UserLogin

@Dao
interface UserLoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserLogin(
        userLogin: UserLogin
    )

    @Query("""
        SELECT *
        FROM UserLogin
    """)

    fun getUserLogin() : LiveData<List<UserLogin>>
    @Query("""
        DELETE
        FROM UserLogin
    """)
    fun deleteAllUserLogin()

}