package com.example.neighbors.models.viewmodels

import android.util.Patterns
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.neighbors.dal.repositories.NeighborRepository
import com.example.neighbors.di.DI
import com.example.neighbors.models.dto.NeighborDto
import com.example.neighbors.ui.fragments.form.FormNeighborHandler
import com.example.neighbors.models.Neighbor
import com.example.neighbors.utils.FieldValidator
import java.util.function.Consumer

class FormNeighborViewModel(owner: LifecycleOwner, private val handler: FormNeighborHandler, modelN: Neighbor?): ViewModel() {

    private val repository: NeighborRepository = DI.repository

    val editMode = modelN != null

    private val model: NeighborDto = if(modelN != null) NeighborDto(modelN) else NeighborDto()

    val imageUrl = FieldViewModel(this.model.avatarUrl)
    val name = FieldViewModel(this.model.name)
    val phone = FieldViewModel(this.model.phoneNumber)
    val website = FieldViewModel(this.model.webSite)
    val address = FieldViewModel(this.model.address)
    val description = FieldViewModel(this.model.aboutMe)

    private val fields = listOf(imageUrl, name, phone, website, address, description)

    val isFormValidMediator = MediatorLiveData<Boolean>()

    init {
        buildRules()
        isFormValidMediator.value = false
        addSource(imageUrl, { model.avatarUrl = it; handler.onValidChangeImage(it);}, { handler.onValidChangeImage(it) })
        addSource(name, { model.name = it })
        addSource(phone, { model.phoneNumber = it })
        addSource(website, { model.webSite = it })
        addSource(address, { model.address = it })
        addSource(description, { model.aboutMe = it })
    }

    private fun <T> addSource(field: FieldViewModel<T>, consumerValid: Consumer<T>, consumerError: Consumer<T>? = null){
        isFormValidMediator.addSource(field.data) {

            if(field.validate())
                consumerValid.accept(it)
            else
                consumerError?.accept(it)

            isFormValidMediator.value = isValid(fields)
        }
    }

    private fun buildRules() {

        imageUrl.addRule("Le champ est obligatoire.") { it.trim().isEmpty() }
        imageUrl.addRule("La valeur doit être une URL.") { !FieldValidator.isUrl(it) }

        name.addRule("Le champ est obligatoire.") { it.trim().isEmpty() }

        phone.addRule("Le champ est obligatoire.") { it.trim().isEmpty() }
        phone.addRule("Le champ n'est pas au format téléphone.") { !FieldValidator.isFrenchPhoneNumber(it) }

        website.addRule("Le champ est obligatoire.") { it.trim().isEmpty() }
        website.addRule("La valeur doit être une URL.") { !FieldValidator.isUrl(it) }

        address.addRule("Le champ est obligatoire.") { it.trim().isEmpty() }
        //address.addRule("La valeur doit être une adresse mail valide.") { !Patterns.EMAIL_ADDRESS.matcher(it).matches() }

        description.addRule("Le champ est obligatoire.") { it.trim().isEmpty() }
        description.addRule("30 caractères maximum.") { it.length > 30 }
    }

    private fun isValid(validators: List<FieldViewModel<*>>): Boolean {
        for (validator in validators) {
            if (!validator.isValid) return false
        }
        return true
    }

    private fun addNeighbor() {
        repository.addNeighbor(model)
    }

    private fun updateNeighbor() {
        repository.updateNeighbor(model)
    }

    fun save() {
        if(model.id == null) //TODO
            addNeighbor();
        else
            updateNeighbor();
    }
}