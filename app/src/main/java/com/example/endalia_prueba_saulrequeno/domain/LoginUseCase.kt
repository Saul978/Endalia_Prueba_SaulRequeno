package com.example.endalia_prueba_saulrequeno.domain

import android.accessibilityservice.AccessibilityService
import com.example.endalia_prueba_saulrequeno.data.network.AutenticacionFirebase
import com.example.endalia_prueba_saulrequeno.data.response.RespuestaLogin
import javax.inject.Inject

/**
 * Login use case
 *  Use case para el logueo del usuario
 * @property authenticationService
 * @constructor Create empty Login use case
 */
class LoginUseCase @Inject constructor(private val authenticationService: AutenticacionFirebase) {

    /**
     * Invoke
     * funcion que se llama al invocar la clase la cual hace el logueo del usuario con los datos que
     * se le pasan
     * @param usuario
     * @param password
     * @return
     */
    suspend operator fun invoke(usuario: String, password: String): RespuestaLogin=
        authenticationService.login(usuario,password)
}