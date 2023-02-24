package com.example.endalia_prueba_saulrequeno.ui.listacontactos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.endalia_prueba_saulrequeno.core.Event
import com.example.endalia_prueba_saulrequeno.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Listado contactos view model
 *
 * @constructor Create empty Listado contactos view model
 */
@HiltViewModel
class ListadoContactosViewModel @Inject constructor() : ViewModel() {

    private val _navigateToLogueo = MutableLiveData<Event<Boolean>>()
    val navigateToLogueo: LiveData<Event<Boolean>>
        get() = _navigateToLogueo

    /**
     * On cerrar session selected
     * Metodo para la seleccion de Cerrar Sesion
     */
    fun onCerrarSessionSelected(){
        _navigateToLogueo.value = Event(true)
    }
}