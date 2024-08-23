package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenu
import Modelo.tbMenuConProductos
import RecyclerViewHelpers.AdaptadorCategoriaMenu
import RecyclerViewHelpers.AdaptadorDetalleMenu
import RecyclerViewHelpers.AdaptadorMenu
import RecyclerViewHelpers.AdaptadorMenuCategorias
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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

class MenuCategoriaActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_categoria)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvMenuCategoria)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val id_menu = intent.getIntExtra("id_menu", 0)
        val categoria = intent.getStringExtra("categoria")
        val id_producto = intent.getIntExtra("id_producto", 0)
        val producto = intent.getStringExtra("producto")
        val descripcion = intent.getStringExtra("descripcion")
        val precioventa = intent.getIntExtra("precioventa", 0)
        val stock = intent.getIntExtra("stock", 0)
        val imagen_categoria = intent.getStringExtra("imagen_categoria")
        val imagen_producto = intent.getStringExtra("nombre_categoria")

        val rvMenuCategoria = findViewById<RecyclerView>(R.id.rvMenuCategoria)
        rvMenuCategoria.layoutManager = LinearLayoutManager(this)

        // Verifica que la categoría no sea nula antes de llamar a obtenerCategorias
        categoria?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val centrosDB = obtenerCategorias(it) // Pasar el valor de categoría
                withContext(Dispatchers.Main) {
                    val miAdapter = AdaptadorCategoriaMenu(centrosDB)
                    rvMenuCategoria.adapter = miAdapter
                }
            }
        } ?: run {
            println("No se recibió la categoría en el intent.")
        }
    }

    suspend fun obtenerCategorias(categoria: String): List<tbMenu> {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val traerCosas = objConexion?.prepareStatement("SELECT Menus_PTC.id_menu, Menus_PTC.categoria, Detalle_Productos_PTC.id_producto, Detalle_Productos_PTC.producto, Detalle_Productos_PTC.descripcion, Detalle_Productos_PTC.precioventa, Detalle_Productos_PTC.stock, Menus_PTC.imagen_categoria, Detalle_Productos_PTC.imagen_comida FROM Menus_PTC INNER JOIN Detalle_Productos_PTC ON Menus_PTC.id_menu = Detalle_Productos_PTC.id_menu where categoria = ?")!!
            traerCosas.setString(1, categoria) // Establecer el parámetro de categoría
            val resultSet = traerCosas.executeQuery()
            val datos = mutableListOf<tbMenu>()

            if (resultSet != null) {
                while (resultSet.next()) {
                    val id_menu = resultSet.getInt("id_menu")
                    val categoriaResult = resultSet.getString("categoria")
                    val imagen_categoria = resultSet.getString("imagen_categoria")
                    val valoresjuntos = tbMenu(
                        id_menu, categoriaResult, imagen_categoria
                    )
                    datos.add(valoresjuntos)
                }
            } else {
                println("La consulta no devolvió resultados.")
            }

            datos
        }
    }
}