package com.example.neighbors.utils

import android.util.Patterns

class FieldValidator {

    companion object {

        fun isFrenchPhoneNumber(str: String) : Boolean {
            return str.length >= 2 &&
                    listOf("06","07").contains(str.subSequence(0,2)) &&
                    str.length == 10
        }

        fun isUrl(str: String) : Boolean {
            return Patterns.WEB_URL.matcher(str).matches()
        }

    }

}