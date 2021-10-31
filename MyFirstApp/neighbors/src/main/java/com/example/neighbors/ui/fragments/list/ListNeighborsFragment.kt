package com.example.neighbors.ui.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neighbors.ui.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.ui.fragments.list.adapter.ListNeighborHandler
import com.example.neighbors.ui.fragments.list.adapter.ListNeighborsAdapter
import com.example.neighbors.databinding.ListNeighborsFragmentBinding
import com.example.neighbors.models.Neighbor
import com.example.neighbors.models.viewmodels.NeighborViewModel
import com.example.neighbors.ui.fragments.detail.DetailNeighborFragment
import com.example.neighbors.ui.fragments.form.FormNeighborFragment
import com.example.neighbors.ui.toolbar.ToolbarState
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

class ListNeighborsFragment(private val navigation: NavigationListener) : Fragment(), ListNeighborHandler {

    private lateinit var binding: ListNeighborsFragmentBinding;
    private lateinit var viewModel: NeighborViewModel
    private lateinit var subscription: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = ListNeighborsFragmentBinding.inflate(inflater, container, false);

        this.viewModel = ViewModelProvider(requireActivity()).get(NeighborViewModel::class.java)

        setLayoutNeighborList()
        setNavigationProperties()

        return binding.root;
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!subscription.isDisposed)
            subscription.dispose()
    }

    private fun setNavigationProperties() {
        navigation.updateTitle(R.string.titleListNeighbor)
        navigation.setToolbarState(ToolbarState.LIST) {
            viewModel.setSelectedNeighbor(null);
            navigation.showFragment(FormNeighborFragment(navigation))
        }
    }

    private fun setLayoutNeighborList() {
        binding.neighborsList.layoutManager = LinearLayoutManager(context)
        binding.neighborsList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.subscription = viewModel.supplier.subscribe {
            supplier -> supplier.observe(viewLifecycleOwner) {
                list -> binding.neighborsList.adapter = ListNeighborsAdapter(list, this)
            }
        }
    }

    override fun onDeleteNeighbor(neighbor: Neighbor) {
        createDialogue(neighbor);
    }

    override fun onUpdateNeighbor(neighbour: Neighbor) {
        viewModel.setSelectedNeighbor(neighbour);
        navigation.showFragment(FormNeighborFragment(navigation))
    }

    override fun onUpdateFav(neighbour: Neighbor) {
        viewModel.updateFav(neighbour)
    }

    override fun onDetailNeighbor(neighbour: Neighbor) {
        viewModel.setSelectedNeighbor(neighbour);
        navigation.showFragment(DetailNeighborFragment(navigation))
    }

    private fun createDialogue(neighbor: Neighbor){
        activity?.let {
           AlertDialog.Builder(it)
               .setMessage(R.string.dialogueRemove)
               .apply {
                    setPositiveButton(R.string.dialogueYes) { _, _ -> viewModel.removeNeighbors(neighbor) }
                    setNegativeButton(R.string.dialogueNo) { _, _ -> }
                }
               .create()
               .show()
        }
    }
}