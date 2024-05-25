package com.example.proyecto.pruebasMenu

data class User(
    val id: String = "",
    val name: String = "",
){
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "user_id" to this.id,
            "display_name" to this.name
        )
    }
}