package com.example.neighbors.ui.fragments.list.adapter

import com.example.neighbors.models.Neighbor

interface ListNeighborHandler {

    fun onDeleteNeighbor(neighbor: Neighbor)
    fun onUpdateNeighbor(neighbour: Neighbor)
    fun onUpdateFav(neighbour: Neighbor)
    fun onDetailNeighbor(neighbour: Neighbor)
    
}