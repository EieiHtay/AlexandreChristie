package com.example.alexandrechristie.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.alexandrechristie.data.entity.WatchOrder
import com.example.alexandrechristie.data.entity.WatchOrderResponse

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(
        order: WatchOrder
    )

    @Query("""
        SELECT watchId,watchCode,price,qty,orderImg,sum(qty) as totalQtyPerFoodId
        FROM WatchOrder  GROUP BY watchId
    """)
    fun searchWatchOrderListing(): LiveData<List<WatchOrderResponse>>

    @Query("SELECT * FROM WatchOrder WHERE id == :id")

    fun getWatchOrderById(id : Int) : LiveData<WatchOrder>

    @Query("DELETE FROM WatchOrder")
    fun deleteAll()

    @Query(" DELETE FROM WatchOrder WHERE watchId = :watchId")
    suspend fun deleteOrder(watchId:Int)
//    @Delete
//    suspend fun deleteOrder(watchOrder: WatchOrder)

}