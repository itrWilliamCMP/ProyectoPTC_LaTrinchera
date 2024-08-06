package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenuConProductos
import RecyclerViewHelpers.AdaptadorMenu
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
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

class Menu_PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Cambiar de pantalla entre vector
        val imgCarrito = findViewById<ImageView>(R.id.imgCarritoOutline)
        imgCarrito.setOnClickListener {
            val pantallaLogin = Intent(this, CarroCompras::class.java)
            startActivity(pantallaLogin)
        }


        val rcvComida = findViewById<RecyclerView>(R.id.rvComidaCategoria)
        rcvComida.layoutManager = LinearLayoutManager(this)

        val imgPrincipalOutline = findViewById<ImageView>(R.id.imgPrincipalOutline)
        imgPrincipalOutline.setOnClickListener {
            val pantallaLogin = Intent(this, MainActivity::class.java)
            startActivity(pantallaLogin)
        }

        val imgPerfilOutline = findViewById<ImageView>(R.id.imgPerfilOutline)
        imgPerfilOutline.setOnClickListener {
            val pantallaLogin = Intent(this, Perfil::class.java)
            startActivity(pantallaLogin)
        }


////////////////////////////////////////////////////////////////////////////////////////

        fun obtenerCategoriasConProductos(): List<tbMenuConProductos> {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery(" SELECT Menus_PTC.id_menu, Menus_PTC.categoria, Detalle_Productos_PTC.id_producto, Detalle_Productos_PTC.producto, Detalle_Productos_PTC.descripcion, Detalle_Productos_PTC.precioventa, Detalle_Productos_PTC.stock, Menus_PTC.imagen_categoria, Detalle_Productos_PTC.imagen_comida FROM Menus_PTC INNER JOIN Detalle_Productos_PTC ON Menus_PTC.id_menu = Detalle_Productos_PTC.id_menu")

            val datos = mutableListOf<tbMenuConProductos>()

            // Manejo de nulos para resultSet
            if (resultSet != null) {
                while (resultSet.next()) {
                    // ... (código para procesar los resultados) ...
                }
            } else {
                // Maneja el caso en que no hay resultados
                println("La consulta no devolvió resultados.")
            }

            return datos
        }
        CoroutineScope(Dispatchers.IO).launch {
            val datos = obtenerCategoriasConProductos() // Obtener datos en un hilo de fondo
            withContext(Dispatchers.Main) { // Volver al hilo principal para actualizar la UI
                if (datos.isNotEmpty()) { // Verificar si hay datos antes de crear el adaptador
                    val adapter = AdaptadorMenu(datos)
                    rcvComida.adapter = adapter
                } else {
                    // Manejar el caso en que no hay datos, por ejemplo, mostrando un mensaje al usuario
                     Toast.makeText(this@Menu_PrincipalActivity, "No se encontraron datos", Toast.LENGTH_SHORT).show()
                    println("No se encontraron datos")
                }
            }
        }
    }
}
