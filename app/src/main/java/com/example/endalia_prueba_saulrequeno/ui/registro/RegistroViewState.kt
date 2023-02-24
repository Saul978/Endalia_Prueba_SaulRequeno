package com.example.endalia_prueba_saulrequeno.ui.registro

/**
 * Registro view state
 *
 * @property isLoading
 * @property isValidUsuario
 * @property isValidPassword
 * @constructor Create empty Registro view state
 */
class RegistroViewState (
    val isLoading: Boolean = false,
    val isValidUsuario: Boolean = true,
    val isValidPassword: Boolean = true
){
    /**
     * User validado
     *
     */
    fun userValidado() = isValidUsuario && isValidPassword
}