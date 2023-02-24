package com.example.endalia_prueba_saulrequeno.ui.listacontactos

import com.example.endalia_prueba_saulrequeno.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.endalia_prueba_saulrequeno.core.adapter.ContactosAdapter
import com.example.endalia_prueba_saulrequeno.data.network.ClienteFirebase
import com.example.endalia_prueba_saulrequeno.databinding.ActivityListadoContactosBinding
import com.example.endalia_prueba_saulrequeno.ui.listacontactos.model.Contactos
import com.example.endalia_prueba_saulrequeno.ui.login.PaginaLogueo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Listado contactos
 * Actividad en la que se ve el listado de contactos guardados en firebase
 * @property firebase
 * @constructor Create empty Listado contactos
 */
@AndroidEntryPoint
class ListadoContactos  : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent {
            val intent = Intent(context, ListadoContactos::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }
    private  lateinit var binding: ActivityListadoContactosBinding
    private val listadoViewModel: ListadoContactosViewModel by viewModels()
    private lateinit var adaptador: ContactosAdapter
    private val listaContactos = ArrayList<Contactos>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListadoContactosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initUI()


    }

    private fun initObservers(){
        listadoViewModel.navigateToLogueo.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToLogueo()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contacto_menu,menu)
        val searchView = menu?.findItem(R.id.buscar)?.actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()){
                    adaptador.filtrar(newText)
                }
                else{
                    adaptador.setlistaFull()
                }
                return true
            }

        })
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when( item.itemId){
            R.id.cerrarSesion-> cerrarSession()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initUI(){
        initRecyclerView()
        initObservers()
    }

    /**
     * Init recycler view
     * inicializa el recycler view con los datos recogidos de firebase y agrega el clicklistener a
     * cada uno de los contactos
     */
    fun initRecyclerView(){
        adaptador= ContactosAdapter(listaContactos)
        binding.recyclerViewContactos.layoutManager=LinearLayoutManager(this)
        binding.recyclerViewContactos.adapter =adaptador
        retrieveAllContactos()
        adaptador.onContactoClick ={
            val intent = Intent(this,ContactoDetails::class.java)
            intent.putExtra("contacto",it)
            startActivity(intent)
        }
    }


    /**
     * Retrieve all contactos
     * Hace el retrieve de todos los contactos de firebase ordenandolos por el apellido y agregandolos
     * a una lista
     */
    @Inject
    lateinit var firebase: ClienteFirebase
    private  fun retrieveAllContactos(){
        CoroutineScope(Dispatchers.IO).launch {

            firebase.database.orderByChild("apellido").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    runOnUiThread{
                        if(!snapshot.hasChildren()) {
                        }
                        snapshot.children.forEach {
                            listaContactos.add(it.getValue(Contactos::class.java)!!)

                        }
                        adaptador.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {            }
            })

        }
        }

    /**
     * Cerrar session
     *
     */
    private fun cerrarSession(){
        FirebaseAuth.getInstance().signOut()
        listadoViewModel.onCerrarSessionSelected()
    }

    /**
     * Go to logueo
     * Una vez cerrada la sesion vuelve a la pagina de logueo
     */
    private fun goToLogueo() {
        startActivity(PaginaLogueo.create(this))
    }
}