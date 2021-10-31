package com.example.neighbors.models.dto

import com.example.neighbors.models.Neighbor

class NeighborDto (
    var id: Long?,
    var name: String,
    var avatarUrl: String,
    var address: String,
    var phoneNumber: String,
    var aboutMe: String,
    var webSite: String,
    var favorite: Boolean
){

    constructor() : this(null, "", "", "", "", "", "", false)

    constructor(model: Neighbor) : this(model.id, model.name, model.avatarUrl, model.address, model.phoneNumber, model.aboutMe, model.webSite, model.favorite)

    fun toModel(): Neighbor{
        return Neighbor(
            this.id ?: -1,
            this.name,
            this.avatarUrl,
            this.address,
            this.phoneNumber,
            this.aboutMe,
            this.favorite,
            this.webSite
        )
    }
}