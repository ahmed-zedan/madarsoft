package com.madarsoft.features.users.presentation.add

import androidx.compose.runtime.Stable
import com.madarsoft.core.TextResource
import com.madarsoft.features.users.domain.entities.UserEntity

@Stable
internal data class AddUserUiState(
    val name: InputFieldState = InputFieldState(),
    val age: InputFieldState = InputFieldState(),
    val job: InputFieldState = InputFieldState(),
    val gender: GenderState = GenderState(),
    val isLoading: Boolean = false
) {
    @Stable
    data class InputFieldState(
        val value: String = "",
        val error: TextResource? = null
    )

    @Stable
    data class GenderState(
        val selected: UserEntity.Gender? = null,
        val error: TextResource? = null
    )
}
