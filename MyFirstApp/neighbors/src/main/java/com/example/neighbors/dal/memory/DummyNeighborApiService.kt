package com.example.neighbors.dal.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbors.dal.NeighborDatasource
import com.example.neighbors.models.Neighbor

class DummyNeighborApiService private constructor() : NeighborDatasource {

    private val DUMMY_NeighborS = DatasMemory.getData()
    private val observable: MutableLiveData<List<Neighbor>> = MutableLiveData(DUMMY_NeighborS)
    private val observableSelect: MutableLiveData<Neighbor?> = MutableLiveData()

    override val neighbours: LiveData<List<Neighbor>>
        get() = observable

    override fun deleteNeighbour(neighbor: Neighbor) {
        DUMMY_NeighborS.remove(neighbor)
        refresh()
    }

    private fun refresh() {
        observable.postValue(DUMMY_NeighborS)
    }

    override fun createNeighbour(neighbor: Neighbor) {
        DUMMY_NeighborS.add(neighbor)
        refresh()
    }

    override fun updateFavoriteStatus(neighbor: Neighbor) {
        updateDataNeighbour(neighbor)
    }

    override fun updateDataNeighbour(neighbor: Neighbor) {
        val index = DUMMY_NeighborS.indexOfFirst { it.id == neighbor.id }
        if(index >= 0){
            DUMMY_NeighborS[index] = neighbor
            observableSelect.postValue(neighbor)
            refresh()
        }
    }

    override fun getById(id: Long): LiveData<Neighbor?> {
        val index = DUMMY_NeighborS.indexOfFirst { it.id == id }
        if(index >= 0){
            observableSelect.value = DUMMY_NeighborS[index]
        }
        return observableSelect
    }

    companion object {
        private var instance: DummyNeighborApiService? = null
        fun getInstance(): DummyNeighborApiService {
            if (instance == null) {
                instance = DummyNeighborApiService()
            }
            return instance!!
        }
    }

}