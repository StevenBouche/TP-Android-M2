package com.example.neighbors.ui.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.R
import com.example.neighbors.models.Neighbor
import com.example.neighbors.utils.Utils

class ListNeighborsAdapter(
    items: List<Neighbor>,
    private val handler: ListNeighborHandler
) : RecyclerView.Adapter<ListNeighborsAdapter.ViewHolder>() {

    private val mNeighbours: List<Neighbor> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.neighbor_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val neighbour: Neighbor = mNeighbours[position]
        // Display Neighbour Name
        holder.mNeighbourName.text = neighbour.name

        val context = holder.itemView.context;

        // Display Neighbour Avatar
        Utils.loadImage(neighbour.avatarUrl, holder.itemView, holder.mNeighbourAvatar)
       /* Glide.with(context)
            .load(neighbour.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_baseline_person_outline_24)
            .error(R.drawable.ic_baseline_person_outline_24)
            .skipMemoryCache(false)
            .into(holder.mNeighbourAvatar)*/

        //setup actions buttons
        holder.mDeleteButton.setOnClickListener { handler.onDeleteNeighbor(neighbour) }
        holder.mUpdateButton.setOnClickListener { handler.onUpdateNeighbor(neighbour) }
        holder.mFavButton.setOnClickListener { handler.onUpdateFav(neighbour) }
        holder.itemView.setOnClickListener { handler.onDetailNeighbor(neighbour) }

        val icon = if(neighbour.favorite) R.drawable.ic_baseline_favorite_border_24 else R.drawable.ic_baseline_favorite_24
        holder.mFavButton.setImageResource(icon)
    }

    override fun getItemCount(): Int {
        return mNeighbours.size
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val mNeighbourAvatar: ImageView
        val mNeighbourName: TextView
        val mDeleteButton: ImageButton
        val mUpdateButton: ImageButton
        val mFavButton: ImageButton

        init {
            // Enable click on item
            mNeighbourAvatar = view.findViewById(R.id.item_list_avatar)
            mNeighbourName = view.findViewById(R.id.item_list_name)
            mDeleteButton = view.findViewById(R.id.item_list_delete_button)
            mUpdateButton = view.findViewById(R.id.item_list_update_button)
            mFavButton = view.findViewById(R.id.item_list_fav_button)
        }

    }

}