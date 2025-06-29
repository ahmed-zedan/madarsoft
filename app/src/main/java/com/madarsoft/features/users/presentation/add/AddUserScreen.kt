@file:OptIn(ExperimentalMaterial3Api::class)

package com.madarsoft.features.users.presentation.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.madarsoft.R
import com.madarsoft.features.users.domain.entities.UserEntity
import com.madarsoft.features.users.presentation.add.AddUserUiState.InputFieldState

@Composable
internal fun AddUserScreen(
    viewModel: AddUserViewModel,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val viewState by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { action ->
            when (action) {
                is AddUserUiEffect.NavigateBack -> onBack.invoke()
                is AddUserUiEffect.ShowToast ->
                    snackbarHostState.showSnackbar(action.message.asString(context))
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopBar { viewModel.sendAction(AddUserUiAction.Back) } },
        bottomBar = {
            SaveButton(
                isLoading = viewState.isLoading,
                onSave = {
                    viewModel.sendAction(AddUserUiAction.Save)
                }
            )
        }
    ) { padding ->
        UserForm(
            state = viewState,
            onAction = viewModel::sendAction,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(R.string.add_user)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
private fun SaveButton(isLoading: Boolean, onSave: () -> Unit) {
    Button(
        onClick = onSave,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = !isLoading
    ) {
        if (isLoading)
            CircularProgressIndicator(Modifier.size(24.dp))
        else
            Text(stringResource(R.string.save))
    }
}

@Composable
private fun UserForm(
    state: AddUserUiState,
    onAction: (AddUserUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        InputField(
            state = state.name,
            onValueChange = { onAction(AddUserUiAction.NameChanged(it)) },
            label = stringResource(R.string.name),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        InputField(
            state = state.age,
            onValueChange = { onAction(AddUserUiAction.AgeChanged(it)) },
            label = stringResource(R.string.age),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        InputField(
            state = state.job,
            onValueChange = { onAction(AddUserUiAction.JobChanged(it)) },
            label = stringResource(R.string.job),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )

        GenderDropdown(
            state = state.gender,
            onSelect = { onAction(AddUserUiAction.GenderSelected(it)) }
        )
    }
}

@Composable
private fun InputField(
    state: InputFieldState,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val error = state.error
    OutlinedTextField(
        value = state.value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = error?.let { { Text(it.asString()) } },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun GenderDropdown(
    state: AddUserUiState.GenderState,
    onSelect: (UserEntity.Gender) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val selected = state.selected
    val error = state.error

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            value = selected?.title ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.gender)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            isError = error != null,
            supportingText = error?.let { { Text(it.asString()) } },
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            UserEntity.Gender.entries.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender.title) },
                    onClick = {
                        onSelect(gender)
                        expanded = false
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}
