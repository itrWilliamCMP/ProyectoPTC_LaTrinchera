package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.Pedido
import Modelo.tbMenu_Repartidor
import RecyclerViewHelpers.Adaptador_Menu_Repartidor
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MenuRepartidor : AppCompatActivity() {

    companion object {
        lateinit var nombreRepartidor: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_repartidor)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rcvRepartidor = findViewById<RecyclerView>(R.id.rcvRepartidor)
        rcvRepartidor.layoutManager = LinearLayoutManager(this)

        obtenerClientes { clientes, error ->
            if (error != null) {
                showError(error)
            } else {
                val adapter = Adaptador_Menu_Repartidor(clientes)
                rcvRepartidor.adapter = adapter
            }
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        val imgBackR = findViewById<ImageView>(R.id.imgBackR)
        imgBackR.setOnClickListener {
            val loginRepartidor = Intent(this, Login_Repartidor::class.java)
            startActivity(loginRepartidor)
        }

        val perfilRepartidorButtons = listOf(
            R.id.PerfilRepartidor1,
            R.id.PerfilRepartidor2,
            R.id.PerfilRepartidor3,
            R.id.PerfilRepartidor4
        )

        perfilRepartidorButtons.forEach { id ->
            findViewById<ImageView>(id).setOnClickListener {
                val menuRepartidor2 = Intent(this, MenuRepartidor2::class.java)
                startActivity(menuRepartidor2)
            }
        }
    }

    private fun obtenerClientes(callback: (List<tbMenu_Repartidor>, String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val statement = objConexion?.createStatement()
                val resultSet = statement?.executeQuery(
                    """
                SELECT 
                    c.id_cliente, c.nombre_clie, c.telefono_clie, c.correoElectronico, c.contrasena, c.direccion_entrega, c.imagen_clientes
                FROM 
                    Clientes_PTC c
                """
                )

                val listaClientes = mutableListOf<tbMenu_Repartidor>()

                while (resultSet?.next() == true) {
                    val idCliente = resultSet.getInt("id_cliente")
                    val nombreCliente = resultSet.getString("nombre_clie") ?: ""
                    val telefonoCliente = resultSet.getString("telefono_clie") ?: ""
                    val correoCliente = resultSet.getString("correoElectronico") ?: ""
                    val contrasenaCliente = resultSet.getString("contrasena") ?: ""
                    val direccionCliente = resultSet.getString("direccion_entrega") ?: ""
                    val imagenCliente = resultSet.getString("imagen_clientes") ?: ""

                    val cliente = tbMenu_Repartidor(
                        idCliente,
                        nombreCliente,
                        telefonoCliente,
                        correoCliente,
                        contrasenaCliente,
                        direccionCliente,
                        imagenCliente,
                        emptyList() // Lista de pedidos vacía por defecto
                    )
                    listaClientes.add(cliente)
                }

                withContext(Dispatchers.Main) {
                    callback(listaClientes, null)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback(emptyList(), e.message)
                }
            }
        }
    }
    private fun showError(error: String?) {
        Toast.makeText(this, "Error al obtener datos: $error", Toast.LENGTH_LONG).show()
        Log.e("MenuRepartidor", "Error: $error")
    }
}