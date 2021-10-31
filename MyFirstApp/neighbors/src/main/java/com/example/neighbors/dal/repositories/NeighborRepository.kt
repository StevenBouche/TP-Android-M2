package com.example.neighbors.dal.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.neighbors.dal.memory.DummyNeighborApiService
import com.example.neighbors.dal.NeighborDatasource
import com.example.neighbors.dal.room.RoomNeighborDataSource
import com.example.neighbors.models.dto.NeighborDto
import com.example.neighbors.models.Neighbor
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import io.reactivex.rxjava3.subjects.BehaviorSubject

class NeighborRepository private constructor(private val application: Application) {

    private val executor: ExecutorService = Executors.newFixedThreadPool(4)

    private var _modeDataSource: ModeDataSource = ModeDataSource.MEMORY
    private var dataSource: NeighborDatasource = getDataSource()
    private val subject: BehaviorSubject<LiveData<List<Neighbor>>> = BehaviorSubject.createDefault(this.dataSource.neighbours)

    val data: Observable<LiveData<List<Neighbor>>>
        get() = this.subject

    var modeDataSource: ModeDataSource
        get() = _modeDataSource
        set(value) {
            _modeDataSource = value
            this.dataSource = getDataSource()
            this.subject.onNext(this.dataSource.neighbours)
        }

    private fun getDataSource() : NeighborDatasource {
        return if(_modeDataSource.value == ModeDataSource.MEMORY.value)
            DummyNeighborApiService.getInstance()
        else
            RoomNeighborDataSource.getInstance(application)
    }

    fun removeNeighbor(n: Neighbor) {
        executor.execute {
            dataSource.deleteNeighbour(n)
        }
    }

    fun addNeighbor(n: NeighborDto) {
        executor.execute {
            n.id = (this.dataSource.neighbours.value?.last()?.id ?: 0) + 1
            dataSource.createNeighbour(n.toModel())
        }
    }

    fun updateNeighbor(n: NeighborDto) {
        executor.execute {
            if (n.id != null)
                dataSource.updateDataNeighbour(n.toModel())
        }
    }

    fun updateFav(n: Neighbor) {
        executor.execute {
            dataSource.updateFavoriteStatus(Neighbor(
                n.id,
                n.name,
                n.avatarUrl,
                n.address,
                n.phoneNumber,
                n.aboutMe,
                !n.favorite,
                n.webSite
            ))
        }
    }

    fun getById(id: Long): LiveData<Neighbor?> {
        return this.dataSource.getById(id)
    }

    companion object {
        private var instance: NeighborRepository? = null
        fun getInstance(application: Application): NeighborRepository {
            if (instance == null) {
                instance = NeighborRepository(application)
            }
            return instance!!
        }
    }
}