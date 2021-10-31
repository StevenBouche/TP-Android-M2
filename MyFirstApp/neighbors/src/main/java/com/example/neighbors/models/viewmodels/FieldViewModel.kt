package com.example.neighbors.models.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.neighbors.utils.validator.DataValidator
import java.util.function.Consumer
import java.util.function.Predicate

class FieldViewModel<T>(data: T) {

    val data: MutableLiveData<T> = MutableLiveData(data)
    private val validator: DataValidator<T> = DataValidator(this.data)
    private var _isValid: Boolean = false

    val isValid: Boolean
        get() = this._isValid

    val error: MutableLiveData<String?>
        get() = this.validator.error

    fun validate(): Boolean{
        this._isValid = this.validator.isValid()
        return isValid;
    }

    fun addRule(errorMsg: String, predicate: Predicate<T>) {
        this.validator.addRule(errorMsg, predicate)
    }

}