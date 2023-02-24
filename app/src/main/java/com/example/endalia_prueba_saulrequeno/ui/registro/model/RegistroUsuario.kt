package com.example.endalia_prueba_saulrequeno.ui.registro.model

/**
 * Registro usuario
 *
 * @property usuario
 * @property password
 * @property passwordRepeticion
 * @constructor Create empty Registro usuario
 */
data class RegistroUsuario(
        val usuario: String,
        val password: String,
        val passwordRepeticion: String
){
    /**
     * Is not empty
     *
     */
    fun isNotEmpty()=
        usuario.isNotEmpty()&& password.isNotEmpty() && passwordRepeticion.isNotEmpty()
}