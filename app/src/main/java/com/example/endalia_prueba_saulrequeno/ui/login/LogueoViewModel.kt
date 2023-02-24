package com.example.endalia_prueba_saulrequeno.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endalia_prueba_saulrequeno.core.Event
import com.example.endalia_prueba_saulrequeno.data.response.RespuestaLogin
import com.example.endalia_prueba_saulrequeno.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Logueo view model
 *
 * @property loginUseCase
 * @constructor Create empty Logueo view model
 */
@HiltViewModel
class LogueoViewModel @Inject constructor(val loginUseCase: LoginUseCase) :ViewModel(){
    private companion object {
        private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }
    private val _navigateToContactos = MutableLiveData<Event<Boolean>>()
    val navigateToContactos: LiveData<Event<Boolean>>
        get() = _navigateToContactos

    private val _dialogError = MutableLiveData<Event<Boolean>>()
    val dialogError: LiveData<Event<Boolean>>
        get() = _dialogError


    private val _navigateToRegistrar = MutableLiveData<Event<Boolean>>()
    val navigateToRegistrar: LiveData<Event<Boolean>>
        get() = _navigateToRegistrar

    private val _verEstado = MutableStateFlow(LogueoViewState())
    val verEstado: StateFlow<LogueoViewState>
        get() = _verEstado

    /**
     * On login selected
     * metodo para loguear una vez pasado las validaciones
     * @param usuario
     * @param password
     */
    fun onLoginSelected(usuario: String, password: String) {
        if (isValidUsuario(usuario) && isValidPassword(password)) {
            logueo(usuario, password)
        } else {
            onFieldsChanged(usuario, password)
        }
    }

    /**
     * On registro selected
     *
     */
    fun onRegistroSelected(){
        _navigateToRegistrar.value = Event(true)
    }

    /**
     * Logueo
     * Metodo para ejecutar la corrutina de logueo y dar respuesta dependiendo de lo recivido
     * @param usuario
     * @param password
     */
    private fun logueo(usuario: String, password: String) {
        viewModelScope.launch {
            _verEstado.value = LogueoViewState(isLoading = true)
            when (val resultado = loginUseCase(usuario, password)) {
                RespuestaLogin.Error -> {
                    _verEstado.value = LogueoViewState(isLoading = false)
                    _dialogError.value = Event(true)
                }

                is RespuestaLogin.Success -> {
                    if (resultado.correcto) {
                        _navigateToContactos.value = Event(true)
                    }
                }
            }
            _verEstado.value = LogueoViewState(isLoading = false)
        }
    }

    /**
     * On fields changed
     * metodo que valida los datos cuando se actualizan los distintos textos del formulario
     * @param usuario
     * @param password
     */
    fun onFieldsChanged(usuario: String, password: String) {
        _verEstado.value = LogueoViewState(
            isValidUsuario = isValidUsuario(usuario),
            isValidPassword = isValidPassword(password)
        )
    }

    /**
     * Is valid usuario
     * Metodo que valida si el usuario es valido
     * @param usuario
     */
    private fun isValidUsuario(usuario: String) =
        EMAIL_ADDRESS_PATTERN.matcher(usuario).matches() ||  usuario.isEmpty()

    /**
     * Is valid password
     * Metodo que valida si la contraseña es valida
     * @param password
     * @return
     */
    private fun isValidPassword(password: String): Boolean =
      password.isNotEmpty()



}