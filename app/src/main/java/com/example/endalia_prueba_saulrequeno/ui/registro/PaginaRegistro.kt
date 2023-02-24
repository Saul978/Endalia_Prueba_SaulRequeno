package com.example.endalia_prueba_saulrequeno.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.endalia_prueba_saulrequeno.R
import com.example.endalia_prueba_saulrequeno.databinding.ActivityPaginaRegistroBinding
import com.example.endalia_prueba_saulrequeno.ui.listacontactos.ListadoContactos
import com.example.endalia_prueba_saulrequeno.ui.registro.RegistroViewModel
import com.example.endalia_prueba_saulrequeno.ui.registro.RegistroViewState
import com.example.endalia_prueba_saulrequeno.ui.registro.model.RegistroUsuario
import dagger.hilt.android.AndroidEntryPoint
import dismissKeyboard
import loseFocusAfterAction
import onTextChanged


/**
 * Pagina registro
 *
 * @constructor Create empty Pagina registro
 */
@AndroidEntryPoint
class PaginaRegistro : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, PaginaRegistro::class.java)
    }

    private  lateinit var binding: ActivityPaginaRegistroBinding
    private val registroViewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPaginaRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    /**
     * Init u i
     * inicializa el UI
     */
    private fun initUI(){
        initListeners()
        initObservers()

    }

    /**
     * Init listeners
     * inicializa los listeners
     */
    private fun initListeners(){
        binding.EditTextUsuarioRegistro.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.EditTextUsuarioRegistro.setOnFocusChangeListener{_,hasFocus-> onFieldChanged(hasFocus)}
        binding.EditTextUsuarioRegistro.onTextChanged { onFieldChanged() }


        binding.EditTextPasswordRegistro.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.EditTextPasswordRegistro.setOnFocusChangeListener{_,hasFocus-> onFieldChanged(hasFocus)}
        binding.EditTextPasswordRegistro.onTextChanged { onFieldChanged() }

        binding.EditTextPasswordValidationRegistro.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
        binding.EditTextPasswordValidationRegistro.setOnFocusChangeListener{_,hasFocus-> onFieldChanged(hasFocus)}
        binding.EditTextPasswordValidationRegistro.onTextChanged { onFieldChanged() }

        binding.registrar.setOnClickListener{
            it.dismissKeyboard()
            registroViewModel.onRegistroSelected(
                RegistroUsuario(
                usuario = binding.EditTextUsuarioRegistro.text.toString(),
                password = binding.EditTextPasswordRegistro.text.toString(),
                passwordRepeticion = binding.EditTextPasswordValidationRegistro.text.toString()
            )
            )
        }

    }

    /**
     * Init observers
     * inicializa los observers
     */
    private fun initObservers(){
        registroViewModel.navigateToContactos.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToContactos()
            }
        }


        lifecycleScope.launchWhenStarted {
            registroViewModel.verEstado.collect { viewState ->
                updateUI(viewState)
            }
        }
    }


    /**
     * On field changed
     *
     * @param hasFocus
     */
    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            registroViewModel.onFieldsChanged(
                registroUsuario = RegistroUsuario(usuario = binding.EditTextUsuarioRegistro.text.toString(),
                    password = binding.EditTextPasswordRegistro.text.toString(),
                    passwordRepeticion = binding.EditTextPasswordValidationRegistro.text.toString()
                )
            )
        }
    }

    /**
     * Update u i
     *
     * @param viewState
     */
    private fun updateUI(viewState: RegistroViewState){
        with(binding){
            InputLayoutUsuarioRegistro.error=
                if (viewState.isValidUsuario)null else getString(R.string.error_usuario)
            InputLayoutPasswordRegistro.error=
                if (viewState.isValidPassword)null else getString(R.string.registro_error_password)
            InputLayoutPasswordValidationRegistro.error=
                if (viewState.isValidPassword)null else getString(R.string.registro_error_password_validation)
        }
    }

    /**
     * Go to contactos
     * Metodo para ir al activity de contactos
     */
    private fun goToContactos() {
        startActivity(ListadoContactos.create(this))
    }

}