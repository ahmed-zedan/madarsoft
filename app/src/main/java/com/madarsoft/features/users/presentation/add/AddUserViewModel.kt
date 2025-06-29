package com.madarsoft.features.users.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madarsoft.R
import com.madarsoft.core.ErrorMapper
import com.madarsoft.core.Ports
import com.madarsoft.core.TextResource
import com.madarsoft.features.users.domain.AddUserUseCase
import com.madarsoft.features.users.domain.entities.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val errorMapper: ErrorMapper,
    private val ports: Ports,
) : ViewModel() {
    private val uiAction = Channel<AddUserUiAction>()

    private val _viewState = MutableStateFlow(AddUserUiState())
    internal val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<AddUserUiEffect>()
    internal val viewEffect = _viewEffect.receiveAsFlow()

    init {
        handleActions()
    }

    internal fun sendAction(action: AddUserUiAction) {
        viewModelScope.launch { uiAction.send(action) }
    }

    private fun handleActions() {
        viewModelScope.launch {
            uiAction.receiveAsFlow().collect { action ->
                when (action) {
                    is AddUserUiAction.Back -> {
                        _viewEffect.send(AddUserUiEffect.NavigateBack)
                    }

                    is AddUserUiAction.NameChanged -> {
                        _viewState.getAndUpdate { state ->
                            state.copy(name = state.name.copy(value = action.name, error = null))
                        }
                    }

                    is AddUserUiAction.AgeChanged -> {
                        _viewState.getAndUpdate { state ->
                            state.copy(age = state.age.copy(value = action.age, error = null))
                        }
                    }

                    is AddUserUiAction.JobChanged -> {
                        _viewState.getAndUpdate { state ->
                            state.copy(job = state.job.copy(value = action.job, error = null))
                        }
                    }

                    is AddUserUiAction.GenderSelected -> {
                        _viewState.getAndUpdate { state ->
                            state.copy(gender = state.gender.copy(selected = action.gender, error = null))
                        }
                    }

                    is AddUserUiAction.Save -> {
                        saveUser()
                    }
                }
            }
        }
    }

    private var saveUserJob: Job? = null
    private fun saveUser() {
        val currentState = viewState.value
        if (isFormInvalid(currentState))
            return

        saveUserJob?.cancel()
        saveUserJob = viewModelScope.launch(ports.io()) {
            try {
                _viewState.getAndUpdate { state -> state.copy(isLoading = true) }
                addUserUseCase.invoke(
                    UserEntity(
                        name = currentState.name.value,
                        age = currentState.age.value,
                        job = currentState.job.value,
                        gender = currentState.gender.selected!!,
                    )
                )
                _viewEffect.send(AddUserUiEffect.NavigateBack)
            } catch (t: Throwable) {
                t.printStackTrace()
                _viewEffect.send(AddUserUiEffect.ShowToast(errorMapper.map(t)))
            } finally {
                _viewState.getAndUpdate { state -> state.copy(isLoading = false) }
            }
        }
    }

    private fun isFormInvalid(state: AddUserUiState): Boolean {
        var updatedState = state
        var hasError = false

        if (state.name.value.isEmpty()) {
            updatedState = updatedState.copy(name = state.name.copy(error = TextResource.fromResource(R.string.name_required)))
            hasError = true
        }
        if (state.age.value.isEmpty()) {
            updatedState = updatedState.copy(age = state.age.copy(error = TextResource.fromResource(R.string.age_required)))
            hasError = true
        }
        if (state.job.value.isEmpty()) {
            updatedState = updatedState.copy(job = state.job.copy(error = TextResource.fromResource(R.string.job_required)))
            hasError = true
        }
        if (state.gender.selected == null) {
            updatedState = updatedState.copy(gender = state.gender.copy(error = TextResource.fromResource(R.string.gender_required)))
            hasError = true
        }

        if (hasError)
            _viewState.value = updatedState


        return hasError
    }
}
