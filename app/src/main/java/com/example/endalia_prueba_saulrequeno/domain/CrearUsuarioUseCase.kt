package com.example.endalia_prueba_saulrequeno.domain

import com.example.endalia_prueba_saulrequeno.data.network.AutenticacionFirebase
import com.example.endalia_prueba_saulrequeno.ui.registro.model.RegistroUsuario
import java.util.concurrent.AbstractExecutorService
import javax.inject.Inject

/**
 * Crear usuario use case
 * Use case para la creacion de usuarios
 * @property authenticationService
 * @constructor Create empty Crear usuario use case
 */
class CrearUsuarioUseCase @Inject constructor(
    private val authenticationService: AutenticacionFirebase
){
    /**
     * Invoke
     * Metodo invoke que se ejecuta al llamar a la clase el cual recive la peticion de creacion de
     * usuario y lo registra en firebase
     * @param registroUsuario
     * @return
     */
    suspend operator fun invoke (registroUsuario: RegistroUsuario):Boolean{
        val accountCreated=
            authenticationService.createAccount(registroUsuario.usuario, registroUsuario.password)!=null
        return if (accountCreated){
            true
        }else{
            false
        }
    }
}