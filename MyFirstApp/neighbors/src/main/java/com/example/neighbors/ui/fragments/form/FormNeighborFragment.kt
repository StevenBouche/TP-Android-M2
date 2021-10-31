package com.example.neighbors.ui.fragments.form

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.ui.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.databinding.AddNeighborBinding
import com.example.neighbors.models.Neighbor
import com.example.neighbors.models.viewmodels.FormNeighborViewModel
import com.example.neighbors.models.viewmodels.FieldViewModel
import com.example.neighbors.models.viewmodels.NeighborViewModel
import com.example.neighbors.ui.fragments.list.ListNeighborsFragment
import com.example.neighbors.ui.toolbar.ToolbarState
import com.example.neighbors.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class FormNeighborFragment(private val navigation: NavigationListener) : Fragment(),
    FormNeighborHandler {

    private lateinit var binding: AddNeighborBinding
    private lateinit var viewModel: NeighborViewModel
    private lateinit var formViewModel: FormNeighborViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = AddNeighborBinding.inflate(inflater, container, false);
        viewModel = ViewModelProvider(requireActivity()).get(NeighborViewModel::class.java)

        viewModel.selectedNeighbor.observe(viewLifecycleOwner) {
            formViewModel = FormNeighborViewModel(this, this, it)
            setNavigationProperties()
            bindViewModelToLayout()
        }

        setListenerButton()

        return binding.root
    }

    private fun setNavigationProperties() {
        val title = if (formViewModel.editMode) R.string.titleEditNeighbor else R.string.titleAddNeighbor
        val state = if(formViewModel.editMode) ToolbarState.EDIT else ToolbarState.NEW;
        navigation.updateTitle(title);
        navigation.setToolbarState(state, null)
    }

    private fun bindViewModelToLayout() {

        setInputTextChange(binding.createImage, binding.createImageInput, formViewModel.imageUrl, { it }, { it })
        setInputTextChange(binding.createName, binding.createNameInput, formViewModel.name, { it }, { it })
        setInputTextChange(binding.createPhone, binding.createPhoneInput, formViewModel.phone, { it }, { it })
        setInputTextChange(binding.createWebsite, binding.createWebsiteInput, formViewModel.website, { it }, { it })
        setInputTextChange(binding.createAdresse, binding.createAdresseInput, formViewModel.address, { it }, { it })
        setInputTextChange(binding.createAbout, binding.createAboutInput, formViewModel.description, { it }, { it })

        //If the form is valid or not set button state
        formViewModel.isFormValidMediator.observe(viewLifecycleOwner, {
            binding.createBtn.isEnabled = it
        })
    }

    private fun setListenerButton() {
        binding.createBtn.setOnClickListener {
            saveModel()
            showSnackbar()
        }
    }

    private fun showSnackbar() {
        Snackbar.make(binding.root, "Successful create neighbor", Snackbar.LENGTH_SHORT)
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                }
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    //navigation.showFragment(ListNeighborsFragment(navigation))
                    navigation.backFragment()
                }
            })
            .setBackgroundTint(Color.GREEN)
            .show()
    }

    private fun saveModel(){
        formViewModel.save()
    }

    private fun <T> setInputTextChange(
        layout: TextInputLayout,
        input: TextInputEditText,
        field: FieldViewModel<T>,
        inputToValue: (value: String) -> T,
        valueToInput: (value: T) -> String,

    ){
        //set current value of model into input
        input.setText(field.data.value?.let { valueToInput(it) })

        //after text change on this input set the data of field
        input.doAfterTextChanged { field.data.value = inputToValue(it.toString()) }

        //on error on this field set into input
        field.error.observe(viewLifecycleOwner, {
            layout.isErrorEnabled = field.isValid
            input.error = it
        })
    }

    override fun onValidChangeImage(url: String) {
        // Display Neighbour Avatar
        Utils.loadImage(url, binding.root, binding.imageView)
    }

}

