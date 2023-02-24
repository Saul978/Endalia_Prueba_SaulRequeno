package com.example.endalia_prueba_saulrequeno.data.network

import com.example.endalia_prueba_saulrequeno.data.response.RespuestaLogin
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Autenticacion firebase
 * Clase en la que se guardan funciones de autenticacion de firebase
 * @property firebase
 * @constructor Create empty Autenticacion firebase
 */
@Singleton
class AutenticacionFirebase @Inject constructor(private val firebase: ClienteFirebase){

    /**
     * Login
     * Hace el login con los parametros que se le pasa y devuelve los datos a respuesta login para
     * validar si ha sido correcto
     * @param usuario
     * @param password
     * @return
     */
    suspend fun login(usuario: String, password: String): RespuestaLogin= runCatching {
        firebase.auth.signInWithEmailAndPassword(usuario,password).await()
    }.toRespuestaLogin()

    /**
     * Create account
     * Crea un usuario con los parametros del nuevo usuario
     * @param usuario
     * @param password
     * @return
     */
    suspend fun createAccount(usuario: String, password: String):AuthResult?{
        return firebase.auth.createUserWithEmailAndPassword(usuario,password).await()
    }

    /**
     * toRespuestaLogin
     * con el resultado de la autenticacion devuelve si ha sido correcto o ha dado error
     * @return
     */
    private fun Result<AuthResult>.toRespuestaLogin()= when (val result= getOrNull()){
        null-> RespuestaLogin.Error
        else-> {
            val Id = result.user
            checkNotNull(Id)
            RespuestaLogin.Success(true)
        }
    }

}


