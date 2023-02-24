package com.example.endalia_prueba_saulrequeno.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Cliente firebase
 * clase con los metodos para instanciar el apartado de auth de firebase y el realtime database tambien
 * @constructor Create empty Cliente firebase
 */
@Singleton
class ClienteFirebase @Inject constructor(){
    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
       val database = FirebaseDatabase.getInstance().getReference("/contactos/contactos")
}