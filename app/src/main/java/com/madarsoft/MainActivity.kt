package com.madarsoft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.madarsoft.features.users.presentation.add.navigation.addUserScreen
import com.madarsoft.features.users.presentation.add.navigation.navigateToAddUser
import com.madarsoft.features.users.presentation.browse.navigation.BrowseUsersRoute
import com.madarsoft.features.users.presentation.browse.navigation.browseUsersScreen
import com.madarsoft.ui.theme.MadarsoftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MadarsoftTheme {
                MadarsoftNavHost(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MadarsoftNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = BrowseUsersRoute, modifier = modifier) {
        browseUsersScreen(
            onAddUser = {
                navController.navigateToAddUser()
            }
        )

        addUserScreen(
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
