package com.example.endalia_prueba_saulrequeno.ui.login

/**
 * Logueo view state
 *
 * @property isLoading
 * @property isValidUsuario
 * @property isValidPassword
 * @constructor Create empty Logueo view state
 */
data class LogueoViewState (
    val isLoading: Boolean = false,
    val isValidUsuario: Boolean = true,
    val isValidPassword: Boolean = true
)