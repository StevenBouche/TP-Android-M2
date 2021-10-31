package com.example.neighbors.models

import com.example.neighbors.dal.room.entities.NeighborEntity

data class Neighbor(
    val id: Long,
    val name: String,
    val avatarUrl: String,
    val address: String,
    val phoneNumber: String,
    val aboutMe: String,
    val favorite: Boolean,
    val webSite: String
) {

    fun toEntity() = NeighborEntity(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
        address = address,
        phoneNumber = phoneNumber,
        aboutMe = aboutMe,
        favorite = favorite,
        webSite = webSite
    )

}