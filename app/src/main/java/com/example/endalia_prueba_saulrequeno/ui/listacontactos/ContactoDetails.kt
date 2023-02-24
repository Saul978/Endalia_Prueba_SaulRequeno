package com.example.endalia_prueba_saulrequeno.ui.listacontactos

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.endalia_prueba_saulrequeno.core.adapter.ContactosAdapter
import com.example.endalia_prueba_saulrequeno.databinding.ActivityContactoDetailsBinding
import com.example.endalia_prueba_saulrequeno.ui.listacontactos.model.Contactos
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

/**
 * Contacto details
 * Actividad en la que se muestra los detalles del contacto seleccionado en el recyclerview
 * @constructor Create empty Contacto details
 */
@Suppress("DEPRECATION")
@AndroidEntryPoint
class ContactoDetails : AppCompatActivity() {


    private  lateinit var binding: ActivityContactoDetailsBinding


    /**
     * Get serializable
     * Funcion para recoger los datos que se han pasado con el inten dependiendo de la version del
     * sdk que se este utilizando
     * @param T
     * @param intent
     * @param key
     * @param m_class
     * @return
     */
    fun <T : Serializable?> getSerializable(intent: Intent, key: String, m_class: Class<T>): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(key, m_class)!!
        else
            intent.getSerializableExtra(key) as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContactoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val contacto = getSerializable(intent, "contacto", Contactos::class.java)

        if( contacto!= null){
            binding.TelefonoContactoDetails.text= contacto.phone
            binding.EmailContactoDetails.text= contacto.email
            binding.NombreContactoDetails.text = contacto.nombre+ " "+ contacto.apellido
            binding.PuestoContactoDetails.text = contacto.puesto
            binding.ImagenDetail.text = contacto.nombre.substring(0,1).uppercase()+ contacto.apellido.substring(0,1).uppercase()
            binding.botonTelefono.setOnClickListener{marcarTelefono(contacto.phone.replace("\\s".toRegex(), ""))}
            binding.botonEmail.setOnClickListener { mandarCorreo(contacto.email) }
        }


    }

    /**
     * Mandar correo
     * lanza el intent a las aplicaciones de mensajeria para mandar el correo con el
     * correo del contacto
     * @param correo
     */
    fun mandarCorreo(correo:String){
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:"+ correo)
        startActivity(intent);
    }

    /**
     * Marcar telefono
     * lanza el intent a las aplicaciones de llamadas para marcar el telefono del contacto en ella
     * @param telefono
     */
    fun marcarTelefono(telefono:String){
        val intent= Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:"+telefono)
        startActivity(intent)
    }
}