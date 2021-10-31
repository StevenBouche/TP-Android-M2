package com.example.neighbors.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.R
import com.example.neighbors.databinding.DetailNeighborBinding
import com.example.neighbors.models.viewmodels.NeighborViewModel
import com.example.neighbors.ui.NavigationListener
import com.example.neighbors.ui.fragments.form.FormNeighborFragment
import com.example.neighbors.ui.toolbar.ToolbarState
import com.example.neighbors.utils.Utils

class DetailNeighborFragment(private val navigation: NavigationListener) : Fragment() {

    private lateinit var binding: DetailNeighborBinding
    private lateinit var viewModel: NeighborViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DetailNeighborBinding.inflate(inflater, container, false);

        viewModel = ViewModelProvider(requireActivity()).get(NeighborViewModel::class.java)

        setNavigationProperties()
        bindModelToLayout()

        return binding.root
    }

    private fun bindModelToLayout() {
        viewModel.selectedNeighbor.observe(viewLifecycleOwner) {
            if(it != null){

                Utils.loadImage(it.avatarUrl, binding.root, binding.imageProfile)

                binding.name.text = it.name
                binding.address.text = it.address
                binding.phone.text = it.phoneNumber
                binding.website.text = it.webSite
                binding.description.text = it.aboutMe

                val image = if(!it.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                binding.imageFavoris.setImageResource(image)
                binding.imageFavoris.setOnClickListener { _ -> viewModel.updateFav(it) }
            }
        }
    }

    private fun setNavigationProperties() {
        navigation.updateTitle(R.string.detail);
        navigation.setToolbarState(ToolbarState.DETAIL) {
            this.navigation.showFragment(FormNeighborFragment(navigation))
        }
    }
}