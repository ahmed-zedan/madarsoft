package com.madarsoft.features.users.domain.entities

data class UserEntity(
    val id: Long = 0,
    val name: String,
    val age: String,
    val job: String,
    val gender: Gender
) {
    enum class Gender {
        MALE,
        FEMALE;
        val title: String
            get() = this.name.lowercase().replaceFirstChar(Char::titlecase)
    }
}
