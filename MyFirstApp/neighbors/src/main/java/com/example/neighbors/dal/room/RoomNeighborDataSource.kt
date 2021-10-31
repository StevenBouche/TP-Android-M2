package com.example.neighbors.dal.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.example.neighbors.dal.NeighborDatasource
import com.example.neighbors.dal.memory.DummyNeighborApiService
import com.example.neighbors.dal.room.daos.NeighborDao
import com.example.neighbors.models.Neighbor

class RoomNeighborDataSource private constructor(application: Application) : NeighborDatasource {

    private val database: NeighborDataBase = NeighborDataBase.getDataBase(application)
    private val dao: NeighborDao = database.neighborDao()

    private val _neighors = MediatorLiveData<List<Neighbor>>()

    init {
        refresh()
    }

    override val neighbours: LiveData<List<Neighbor>>
        get() = _neighors

    private fun refresh(){
        _neighors.addSource(dao.getNeighbors()) { entities ->
            _neighors.value = entities.map { entity ->
                entity.toNeighbor()
            }
        }
    }

    override fun deleteNeighbour(neighbor: Neighbor) {
        dao.delete(neighbor.toEntity())
    }

    override fun createNeighbour(neighbor: Neighbor) {
        dao.add(neighbor.toEntity())
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        dao.update(neighbor.toEntity())
    }

    override fun updateDataNeighbour(neighbor: Neighbor) {
        dao.update(neighbor.toEntity())
    }

    override fun getById(id: Long): LiveData<Neighbor?> {
        return dao.getById(id).map { it?.toNeighbor() }
    }

    companion object {
        private var instance: RoomNeighborDataSource? = null
        fun getInstance(application: Application): RoomNeighborDataSource {
            if (instance == null) {
                instance = RoomNeighborDataSource(application)
            }
            return instance!!
        }
    }
}