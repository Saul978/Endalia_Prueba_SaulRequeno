package com.example.endalia_prueba_saulrequeno.data.response

/**
 * Respuesta login
 * clase para responder con el resultado del login
 * @constructor Create empty Respuesta login
 */
sealed class RespuestaLogin {
    object Error: RespuestaLogin()

    /**
     * Success
     *
     * @property correcto
     * @constructor Create empty Success
     */
    data class  Success(val correcto:Boolean) : RespuestaLogin()
}