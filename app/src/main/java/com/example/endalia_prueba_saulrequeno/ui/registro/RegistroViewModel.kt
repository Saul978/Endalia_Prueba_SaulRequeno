package com.example.endalia_prueba_saulrequeno.ui.registro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.endalia_prueba_saulrequeno.core.Event
import com.example.endalia_prueba_saulrequeno.domain.CrearUsuarioUseCase
import com.example.endalia_prueba_saulrequeno.ui.registro.model.RegistroUsuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject


/**
 * Registro view model
 *
 * @property crearUsuarioUseCase
 * @constructor Create empty Registro view model
 */
@HiltViewModel
class RegistroViewModel @Inject constructor(val crearUsuarioUseCase: CrearUsuarioUseCase) :ViewModel(){

    private companion object{
        private val MIN_PASSWORD= Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //al menos un digito
                    "(?=.*[a-z])" +         //al menos una letra  minuscula
                    "(?=.*[A-Z])" +         //al menos una letra  mayuscula
                    "(?=.*[a-zA-Z])" +      //cualquier letra
                    "(?=.*[\$&+,:;=?@#|'<>.^*()%!-])" +
                    "(?=\\S+$)" +//no espacios en blanco
                    ".{8,}" +               //como minimo 4 caracteres
                    "$"
        )
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


    private val _verEstado = MutableStateFlow(RegistroViewState())
    val verEstado: StateFlow<RegistroViewState>
        get() = _verEstado

    /**
     * On registro selected
     *
     * @param registroUsuario
     */
    fun onRegistroSelected(registroUsuario: RegistroUsuario) {
        val viewState = registroUsuario.toSignInViewState()
        if (viewState.userValidado()&& registroUsuario.isNotEmpty() ) {
            registrarUsuario(registroUsuario)
        } else {
            onFieldsChanged(registroUsuario)
        }
    }

    /**
     * Registrar usuario
     * metodo que lanza una corrutina para el registro del usuario
     * @param registroUsuario
     */
    private fun registrarUsuario(registroUsuario: RegistroUsuario){
        viewModelScope.launch {
            _verEstado.value = RegistroViewState(isLoading = true)
            val cuentaCreada = crearUsuarioUseCase(registroUsuario)
            if (cuentaCreada){
                _navigateToContactos.value = Event(true)
            }
            _verEstado.value = RegistroViewState(isLoading = false)
        }

    }

    /**
     * On fields changed
     *
     * @param registroUsuario
     */
    fun onFieldsChanged(registroUsuario: RegistroUsuario) {
        _verEstado.value = registroUsuario.toSignInViewState()

    }

    /**
     * Is valid usuario
     * metodo que valida el
     * @param usuario
     */
    private fun isValidUsuario(usuario: String) =
        EMAIL_ADDRESS_PATTERN.matcher(usuario).matches() || usuario.isEmpty()

    /**
     * Is valid password
     * valida las contraseñas tanto como la original como la validacion de la contraseña
     * @param password
     * @param passwordRepeticion
     * @return
     */
    private fun isValidPassword(password: String, passwordRepeticion: String): Boolean =
        MIN_PASSWORD.matcher(password).matches() && password ==passwordRepeticion|| password.isEmpty() || passwordRepeticion.isEmpty()

    /**
     * To sign in view state
     *
     * @return
     */
    private fun RegistroUsuario.toSignInViewState(): RegistroViewState {
        return RegistroViewState(
            isValidUsuario = isValidUsuario(usuario),
            isValidPassword = isValidPassword(password, passwordRepeticion )

        )
    }

}