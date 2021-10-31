package com.example.neighbors.models.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neighbors.di.DI
import com.example.neighbors.dal.repositories.NeighborRepository
import com.example.neighbors.models.Neighbor
import io.reactivex.rxjava3.core.Observable

class NeighborViewModel : ViewModel() {

    private val repository: NeighborRepository = DI.repository
    private var _selected: LiveData<Neighbor?> = MutableLiveData()

    val supplier: Observable<LiveData<List<Neighbor>>>
        get() = repository.data

    val selectedNeighbor: LiveData<Neighbor?>
        get() = _selected

    fun updateFav(neighbour: Neighbor) {
        repository.updateFav(neighbour)
    }

    fun removeNeighbors(neighbor: Neighbor) {
       repository.removeNeighbor(neighbor)
    }

    fun setSelectedNeighbor(n: Neighbor?){
        this._selected = if(n == null) MutableLiveData(null) else repository.getById(n.id)
    }

}