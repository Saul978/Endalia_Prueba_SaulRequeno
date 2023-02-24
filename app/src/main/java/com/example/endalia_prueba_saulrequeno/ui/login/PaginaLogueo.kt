package com.example.endalia_prueba_saulrequeno.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.endalia_prueba_saulrequeno.R
import com.example.endalia_prueba_saulrequeno.databinding.ActivityMainBinding
import com.example.endalia_prueba_saulrequeno.ui.listacontactos.ListadoContactos
import dagger.hilt.android.AndroidEntryPoint
import dismissKeyboard
import loseFocusAfterAction
import onTextChanged


/**
 * Pagina logueo
 *
 * @constructor Create empty Pagina logueo
 */
@AndroidEntryPoint
class PaginaLogueo : AppCompatActivity() {
    companion object{

        fun create(context: Context): Intent {
            val intent = Intent(context, PaginaLogueo::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }
    private  lateinit var binding: ActivityMainBinding
    private val loginViewModel: LogueoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        }

    /**
     * Init u i
     * metodo para inicializar la interfaz de usuario
     */
    private fun initUI(){
        initListeners()
        initObservers()

    }

    /**
     * Init listeners
     * inicializa los listener de la activity
     */
    private fun initListeners(){
        binding.EditTextUsuario.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
        binding.EditTextUsuario.onTextChanged { onFieldChanged() }


        binding.EditTextPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
        binding.EditTextPassword.setOnFocusChangeListener{_,hasFocus-> onFieldChanged(hasFocus)}
        binding.EditTextPassword.onTextChanged { onFieldChanged() }

        binding.login.setOnClickListener{
            it.dismissKeyboard()
            loginViewModel.onLoginSelected(
                binding.EditTextUsuario.text.toString(),
                binding.EditTextPassword.text.toString()
            )
        }
        binding.buttonRegistro.setOnClickListener { (loginViewModel.onRegistroSelected()) }
    }

    /**
     * Init observers
     * inicializa los observer de la activity
     */
    private fun initObservers(){
            loginViewModel.navigateToRegistrar.observe(this) {
                it.getContentIfNotHandled()?.let {
                    goToRegistro()
                }
            }
        loginViewModel.dialogError.observe(this) {
            it.getContentIfNotHandled()?.let {
                dialogoErrorLogueo()
            }
        }
        loginViewModel.navigateToContactos.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToContactos()
            }
        }


        lifecycleScope.launchWhenStarted {
                loginViewModel.verEstado.collect { viewState ->
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
            loginViewModel.onFieldsChanged(
                usuario = binding.EditTextUsuario.text.toString(),
                password = binding.EditTextPassword.text.toString()
            )
        }
    }

    /**
     * Update u i
     * actualiza la ui con cada input
     * @param viewState
     */
    private fun updateUI(viewState: LogueoViewState){
        with(binding){
          TextLayoutUsuario.error=
              if (viewState.isValidUsuario)null else getString(R.string.error_usuario)
          TextLayoutPassword.error=
              if (viewState.isValidPassword)null else getString(R.string.error_password)
        }
    }


    /**
     * Go to registro
     * metodo para ir a la activity registro
     */
    private fun goToRegistro() {
        startActivity(PaginaRegistro.create(this))
    }

    /**
     * Go to contactos
     * metodo para ir a la activity ListadoContactos
     */
    private fun goToContactos() {
        startActivity(ListadoContactos.create(this))
    }


    /**
     * Dialogo error
     * Muestra el dialog con el error al fallar el inicio de sesion
     */
    fun dialogoErrorLogueo(){
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.titulo_error_logueo))
            setMessage(getString(R.string.error_logueo))
        }.create().show()
    }
}