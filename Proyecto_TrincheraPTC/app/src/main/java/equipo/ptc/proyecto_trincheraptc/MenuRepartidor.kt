package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenu_Repartidor
import RecyclerViewHelpers.Adaptador_Menu_Repartidor
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuRepartidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_repartidor)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura el RecyclerView
        val rcvRepartidor = findViewById<RecyclerView>(R.id.rcvRepartidor)
        rcvRepartidor.layoutManager = LinearLayoutManager(this)

        // Llama a la función para obtener los clientes
        obtenerClientes { clientes ->
            // Asigna el adaptador al RecyclerView
            val adapter = Adaptador_Menu_Repartidor(clientes)
            rcvRepartidor.adapter = adapter
        }

        // Configura los clics para otras imágenes
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

    // Aquí es donde debes incluir la función obtenerClientes
    private fun obtenerClientes(callback: (List<tbMenu_Repartidor>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM Clientes_PTC")

            val listaClientes = mutableListOf<tbMenu_Repartidor>()

            while (resultSet?.next() == true) {
                val id_cliente = resultSet.getInt("id_cliente")
                val nombre_clie = resultSet.getString("nombre_clie")
                val telefono_clie = resultSet.getString("telefono_clie")
                val correoElectronico = resultSet.getString("correoElectronico")
                val contrasena = resultSet.getString("contrasena")
                val direccion_entrega = resultSet.getString("direccion_entrega")
                val imagen_clientes = resultSet.getString("imagen_clientes") // Obtener imagen

                val cliente = tbMenu_Repartidor(
                    id_cliente,
                    nombre_clie,
                    telefono_clie,
                    correoElectronico,
                    contrasena,
                    direccion_entrega,
                    imagen_clientes // Pasar imagen al objeto
                )
                listaClientes.add(cliente)
            }

            // Llama al callback en el hilo principal
            withContext(Dispatchers.Main) {
                callback(listaClientes)
            }
        }
    }
}