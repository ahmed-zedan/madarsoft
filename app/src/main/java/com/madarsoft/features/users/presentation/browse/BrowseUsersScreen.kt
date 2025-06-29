@file:OptIn(ExperimentalMaterial3Api::class)

package com.madarsoft.features.users.presentation.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.madarsoft.R
import com.madarsoft.features.users.domain.entities.UserEntity

@Composable
fun BrowseUsersScreen(
    viewModel: BrowseUsersViewModel,
    onAddUser: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.browse_users)) }
            )
        },
        floatingActionButton = {
            AddUserButton(
                modifier = Modifier,
                onClick = onAddUser
            )
        }
    ) { innerPadding ->
        val users by viewModel.users.collectAsState()
        BrowseUsersContent(
            users = users,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun AddUserButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_user)
        )
    }
}

@Composable
private fun BrowseUsersContent(
    users: List<UserEntity>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        items(users) { user ->
            UserItem(
                user = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserItem(
    user: UserEntity,
    modifier: Modifier = Modifier
) {
    OutlinedCard {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
            UserItemRowField(
                title = stringResource(R.string.name),
                value = user.name
            )
            UserItemRowField(
                title = stringResource(R.string.age),
                value = user.age
            )
            UserItemRowField(
                title = stringResource(R.string.job),
                value = user.job
            )
            UserItemRowField(
                title = stringResource(R.string.gender),
                value = user.gender.title
            )
        }
    }
}

@Composable
private fun UserItemRowField(title: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title, fontWeight = FontWeight.SemiBold)
        Text(text = value)
    }
}

