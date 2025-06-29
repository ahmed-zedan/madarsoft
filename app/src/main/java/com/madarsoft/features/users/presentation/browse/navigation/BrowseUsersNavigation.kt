package com.madarsoft.features.users.presentation.browse.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.madarsoft.features.users.presentation.browse.BrowseUsersScreen
import kotlinx.serialization.Serializable

@Serializable
data object BrowseUsersRoute

fun NavGraphBuilder.browseUsersScreen(onAddUser: () -> Unit) {
    composable<BrowseUsersRoute> {
        BrowseUsersScreen(
            viewModel = hiltViewModel(),
            onAddUser = onAddUser
        )
    }
}
