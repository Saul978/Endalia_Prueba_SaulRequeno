package com.example.endalia_prueba_saulrequeno.core.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.endalia_prueba_saulrequeno.R
import com.example.endalia_prueba_saulrequeno.ui.listacontactos.model.Contactos

/**
 * Contactos view holder
 *
 * @constructor
 *
 * @param view
 */
class ContactosViewHolder(view: View):RecyclerView.ViewHolder(view){

    val imagen = view.findViewById<TextView>(R.id.ImagenDetail)
    val nombre = view.findViewById<TextView>(R.id.NombreUsuario)
    val puesto = view.findViewById<TextView>(R.id.PuestoTrabajo)

    /**
     * Renderizar
     * Renderiza los contactos del ViewHolder con nada uno de los contactos agregando los datos
     * @param contactos
     */
    fun renderizar(contactos: Contactos){
        val perfilLetra = contactos.nombre.substring(0,1).uppercase()+ contactos.apellido.substring(0,1).uppercase()
        imagen.text = perfilLetra
        nombre.text = contactos.nombre + ", "+ contactos.apellido
        puesto.text = contactos.puesto
    }

}