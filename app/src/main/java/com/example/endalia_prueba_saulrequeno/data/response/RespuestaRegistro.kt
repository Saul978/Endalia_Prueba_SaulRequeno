package com.example.endalia_prueba_saulrequeno.data.response

/**
 * Respuesta Registro
 * clase para responder con el resultado del registro
 * @constructor Create empty Respuesta Registro
 */
sealed class RespuestaRegistro {
    object Error: RespuestaRegistro()

    /**
     * Success
     *
     * @property correcto
     * @constructor Create empty Success
     */
    data class  Success(val correcto:Boolean) : RespuestaRegistro()
}