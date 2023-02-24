package com.example.endalia_prueba_saulrequeno.core.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.endalia_prueba_saulrequeno.R
import com.example.endalia_prueba_saulrequeno.ui.listacontactos.model.Contactos

/**
 * Contactos adapter
 *
 * @property listaContactos
 * @constructor Create empty Contactos adapter
 */
class ContactosAdapter(var listaContactos: ArrayList<Contactos>) : RecyclerView.Adapter<ContactosViewHolder>(){

    private val  listaContactosCompleta: ArrayList<Contactos> = listaContactos
    var onContactoClick : ((Contactos)-> Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactosViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ContactosViewHolder(layoutInflater.inflate(R.layout.item_contactos,parent,false))
    }

    override fun onBindViewHolder(holder: ContactosViewHolder, position: Int) {
        val item= listaContactos[position]
        holder.renderizar(item)

        holder.itemView.setOnClickListener{
            onContactoClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = listaContactos.size

    /**
     * Filtrar
     * Filtra entre todos los items del RecyclerView con el parametro que se la ha pasado
     * filtrando nombre , apellido y puesto de trabajo
     * @param busqueda
     */
    fun filtrar(busqueda:String){
        if(!busqueda.isNullOrEmpty()){
            val listafiltrada = ArrayList<Contactos>()
            listaContactosCompleta.forEach{
                if (it.nombre.lowercase().contains(busqueda)||it.puesto.lowercase().contains(busqueda)||it.apellido.lowercase().contains(busqueda)){
                    listafiltrada.add(it)
                }
            }
            if (listafiltrada.isEmpty()){

            }else{
                setListaFiltrada(listafiltrada)
            }
        }
    }

    /**
     * Set lista filtrada
     * Actualiza la lista del Recyclerview con la lista que se le pasa siendo
     * @param listaContactos
     */
    fun setListaFiltrada(listaContactos: ArrayList<Contactos>){
        this.listaContactos= listaContactos
        notifyDataSetChanged()
    }

    /**
     * Setlista full
     * actualiza la lista con los datos completos de la base de datos
     */
    fun setlistaFull(){
        this.listaContactos= listaContactosCompleta
        notifyDataSetChanged()
    }


}