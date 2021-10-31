package com.example.neighbors.dal.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.neighbors.models.Neighbor

@Entity(tableName = "neighbors")
class NeighborEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var name: String,
    var avatarUrl: String,
    val address: String,
    var phoneNumber: String,
    var aboutMe: String,
    var favorite: Boolean = false,
    var webSite: String? = null,
) {
    fun toNeighbor() = Neighbor(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
        address = address,
        phoneNumber = phoneNumber,
        aboutMe = aboutMe,
        favorite = favorite,
        webSite = webSite ?: ""
    )
}