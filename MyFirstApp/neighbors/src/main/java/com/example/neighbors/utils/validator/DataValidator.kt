package com.example.neighbors.utils.validator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.function.Predicate

class DataValidator<T>(private val liveData: LiveData<T>) {

    private val validationRules = mutableListOf<Predicate<T>>()
    private val errorMessages = mutableListOf<String>()

    var error = MutableLiveData<String?>()

    fun isValid(): Boolean {
        for (i in 0 until validationRules.size) {

            if(liveData.value == null)
                return false;

            if(validationRules[i].test(liveData.value!!)){
                emitErrorMessage(errorMessages[i])
                return false
            }
        }
        emitErrorMessage(null)
        return true
    }

    //For emitting error messages
    private fun emitErrorMessage(messageRes: String?) {
        error.value = messageRes
    }

    //For adding validation rules
    fun addRule(errorMsg: String, predicate: Predicate<T>) {
        validationRules.add(predicate)
        errorMessages.add(errorMsg)
    }
}