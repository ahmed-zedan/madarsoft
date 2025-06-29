package com.madarsoft.features.users.presentation.add

import com.madarsoft.features.users.domain.entities.UserEntity

internal sealed class AddUserUiAction {
    data object Back: AddUserUiAction()
    data class NameChanged(val name: String) : AddUserUiAction()
    data class AgeChanged(val age: String) : AddUserUiAction()
    data class JobChanged(val job: String) : AddUserUiAction()
    data class GenderSelected(val gender: UserEntity.Gender) : AddUserUiAction()
    data object Save: AddUserUiAction()
}
