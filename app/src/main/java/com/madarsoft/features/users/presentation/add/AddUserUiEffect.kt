package com.madarsoft.features.users.presentation.add

import com.madarsoft.core.TextResource

internal sealed class AddUserUiEffect {
    object NavigateBack : AddUserUiEffect()
    data class ShowToast(val message: TextResource) : AddUserUiEffect()
}
