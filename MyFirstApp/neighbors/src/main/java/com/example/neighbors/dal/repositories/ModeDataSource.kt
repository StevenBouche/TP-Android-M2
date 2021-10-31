package com.example.neighbors.dal.repositories

enum class ModeDataSource(val value: Long, var text: String) {

    MEMORY(1, "MEMORY"), DATABASE(2, "DATABASE");

    companion object{

        fun getModeSource(value: Long): ModeDataSource? {
            return values().firstOrNull { it.value == value };
        }

        fun getModeFunctionOfCheck(value: Boolean): ModeDataSource {
            return if(value) DATABASE else MEMORY;
        }

    }
}