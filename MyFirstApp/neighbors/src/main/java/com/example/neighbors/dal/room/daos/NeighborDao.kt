package com.example.neighbors.dal.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.neighbors.dal.room.entities.NeighborEntity

@Dao
interface NeighborDao {

    @Query("SELECT * from neighbors")
    fun getNeighbors(): LiveData<List<NeighborEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: NeighborEntity)

    @Delete
    fun delete(users: NeighborEntity)

    @Update
    fun update(users: NeighborEntity)

    @Query("SELECT * FROM neighbors WHERE id=:id ")
    fun getById(id: Long): LiveData<NeighborEntity?>

}