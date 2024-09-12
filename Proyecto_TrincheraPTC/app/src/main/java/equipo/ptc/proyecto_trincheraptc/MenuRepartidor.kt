package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenu_Repartidor
import RecyclerViewHelpers.AdaptadorMenuCategorias
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

        val PerfilRepartidor1 = findViewById<ImageView>(R.id.PerfilRepartidor1)
        PerfilRepartidor1.setOnClickListener {

            val MenuRepartidor2 = Intent(this, MenuRepartidor2::class.java)
            startActivity(MenuRepartidor2)

            val rcvRepartidor = findViewById<RecyclerView>(R.id.rcvRepartidor)

            rcvRepartidor.layoutManager = LinearLayoutManager(this)

            ////TODO: mostrar datos

            fun obtenerClientes(): List<tbMenu_Repartidor> {

                val objConexion = ClaseConexion().cadenaConexion()

                val statement = objConexion?.createStatement()
                val resultSet = statement?.executeQuery("SELECT * FROM tbMenuRepartidor")!!

                val listaClientes = mutableListOf<tbMenu_Repartidor>()

                while (resultSet.next()) {
                    val uuid = resultSet.getString("uuid")
                    val nombre_clie = resultSet.getString("nombre_clie")
                    val telefono_clie = resultSet.getInt("telefono_clie")
                    val correoElectronico = resultSet.getString("correoElectronico")
                    val contrasena = resultSet.getInt("contrasena")
                    val direccion_entrega = resultSet.getString("direccion_entrega")

                    val valoresJuntos = tbMenu_Repartidor(
                        uuid,
                        nombre_clie,
                        telefono_clie.toString(),
                        correoElectronico,
                        contrasena.toString(),
                        direccion_entrega
                    )

                    listaClientes.add(valoresJuntos)
                }
                return listaClientes
            }


            //Asignar adaptador al RecycleView
            CoroutineScope(Dispatchers.IO).launch {
                val ClientesDB = obtenerClientes()
                withContext(Dispatchers.Main) {
                    val adapter = Adaptador_Menu_Repartidor(ClientesDB)
                }
            }
        }
    }
}
