package com.example.alexandrechristie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alexandrechristie.data.entity.ViewOrder
import com.example.alexandrechristie.data.entity.WatchOrder
import com.example.alexandrechristie.data.entity.WatchOrderResponse

@Dao
interface ViewOrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewOrder(
        viewOrder: ViewOrder
    )

    @Query("""
        SELECT watchId,watchCode,price,sum(qty) as qty,total,orderImg,userName,phone,email,orderDate
        FROM ViewOrder 
        WHERE email == :email
        GROUP BY orderDate,watchCode
        """)

    fun getViewOrderByEmail(email : String) : LiveData<List<ViewOrder>>
}