package com.madarsoft.features.users.presentation.add.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.madarsoft.features.users.presentation.add.AddUserScreen
import kotlinx.serialization.Serializable

@Serializable
data object AddUserRoute

fun NavGraphBuilder.addUserScreen(
    onBack: () -> Unit
) {
    composable<AddUserRoute> {
        AddUserScreen(
            viewModel = hiltViewModel(),
            onBack = onBack
        )
    }
}

fun NavController.navigateToAddUser(navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = AddUserRoute) {
        navOptions()
    }
}
