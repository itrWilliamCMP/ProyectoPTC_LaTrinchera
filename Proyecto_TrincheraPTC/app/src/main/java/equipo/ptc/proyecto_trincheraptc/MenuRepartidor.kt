package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
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

    private lateinit var rcvRepartidor: RecyclerView
    private lateinit var adaptador: Adaptador_Menu_Repartidor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_repartidor)

        setupViews()
        setupEdgeToEdge()
        obtenerClientes()
        setupClickListeners()
    }

    private fun setupViews() {
        rcvRepartidor = findViewById(R.id.rcvRepartidor)
        rcvRepartidor.layoutManager = LinearLayoutManager(this)
    }

    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun obtenerClientes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val statement = objConexion?.createStatement()
                val resultSet = statement?.executeQuery(
                    """
                    SELECT 
                        c.id_cliente, c.nombre_clie, c.telefono_clie, c.correoElectronico, 
                        c.contrasena, c.direccion_entrega, c.imagen_clientes
                    FROM 
                        Clientes_PTC c
                    """
                )

                val listaClientes = mutableListOf<tbMenu_Repartidor>()

                while (resultSet?.next() == true) {
                    val cliente = tbMenu_Repartidor(
                        resultSet.getInt("id_cliente"),
                        resultSet.getString("nombre_clie") ?: "",
                        resultSet.getString("telefono_clie") ?: "",
                        resultSet.getString("correoElectronico") ?: "",
                        resultSet.getString("contrasena") ?: "",
                        resultSet.getString("direccion_entrega") ?: "",
                        resultSet.getString("imagen_clientes") ?: "",
                        emptyList() // Lista de pedidos vacía por defecto
                    )
                    listaClientes.add(cliente)
                }

                withContext(Dispatchers.Main) {
                    adaptador = Adaptador_Menu_Repartidor(listaClientes)
                    rcvRepartidor.adapter = adaptador
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError(e.message)
                }
            }
        }
    }

    private fun setupClickListeners() {
        findViewById<ImageView>(R.id.imgBackR).setOnClickListener {
            startActivity(Intent(this, Login_Repartidor::class.java))
        }

        findViewById<ImageView>(R.id.PerfilRepartidor3).setOnClickListener {
            startActivity(Intent(this, MenuRepartidor2::class.java))
        }
    }

    private fun showError(error: String?) {
        Toast.makeText(this, "Error al obtener datos: $error", Toast.LENGTH_LONG).show()
        Log.e("MenuRepartidor", "Error: $error")
    }
}