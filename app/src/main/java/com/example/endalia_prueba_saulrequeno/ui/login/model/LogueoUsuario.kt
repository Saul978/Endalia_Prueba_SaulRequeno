package com.example.endalia_prueba_saulrequeno.ui.login.model

/**
 * Logueo usuario
 *
 * @property usuario
 * @property password
 * @property mostrarError
 * @constructor Create empty Logueo usuario
 */
data class LogueoUsuario (
    val usuario:String ="",
    val password:String ="",
    val mostrarError: Boolean = false
)