package com.example.endalia_prueba_saulrequeno.ui.listacontactos.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


/**
 * Contactos
 *
 * @property _id
 * @property apellido
 * @property email
 * @property imagen
 * @property nombre
 * @property puesto
 * @property phone
 * @constructor Create empty Contactos
 */
data class Contactos (val _id: String, val  apellido: String, val email: String, val imagen: String, val  nombre: String,
                      val puesto:String, val phone:String): Serializable
{
    constructor():this("","","","","","","")
}

