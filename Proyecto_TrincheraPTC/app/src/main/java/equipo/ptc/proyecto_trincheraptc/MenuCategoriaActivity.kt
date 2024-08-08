package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenuConProductos
import RecyclerViewHelpers.AdaptadorMenu
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
        val categoria = intent.getStringExtra("nombre_menu")
        val id_producto = intent.getIntExtra("id_producto", 0)
        val producto = intent.getStringExtra("producto")
        val descripcion = intent.getStringExtra("descripcion")
        val precioventa = intent.getIntExtra("precioventa",0 )
        val stock = intent.getIntExtra("stock",0)
        val imagen_categoria = intent.getStringExtra("imagen_categoria")
        val imagen_producto = intent.getStringExtra("nombre_categoria")

//        tvNombreProducto.text = producto

        //
        fun obtenerCategorias(): List<tbMenuConProductos> {
            val objConexion = ClaseConexion().cadenaConexion()
            val traerCosas = objConexion?.prepareStatement("select * from Detalle_Productos_PTC where id_menu = ?")!!
            traerCosas.setInt(1, id_menu)
            val resultSet = traerCosas.executeQuery()


            val datos = mutableListOf<tbMenuConProductos>()

            // Manejo de nulos para resultSet
            if (resultSet != null) {
                while (resultSet.next()) {
                    val id_menu = resultSet.getInt("id_menu")
                    val categoria = resultSet.getString("categoria")
                    val id_producto = resultSet.getInt("id_producto")
                    val producto = resultSet.getString("producto")
                    val descripcion = resultSet.getString("descripcion")
                    val precioventa = resultSet.getDouble("precioventa")
                    val stock = resultSet.getInt("stock")
                    val imagen_categoria =resultSet.getString("imagen_categoria")
                    val imagen_producto = resultSet.getString("imagen_comida")

                    val valoresjuntos = tbMenuConProductos(
                        id_menu, categoria, id_producto, producto, descripcion,
                        precioventa, stock, imagen_categoria, imagen_producto
                    )
                    datos.add(valoresjuntos)
                }
            } else {
                // Maneja el caso en que no hay resultados
                println("La consulta no devolvió resultados.")
            }

            return datos
//        val adapter = AdaptadorMenuCategorias(comidas)
//        recyclerView.adapter = adapter

        }
        CoroutineScope(Dispatchers.IO).launch {
            val datos = obtenerCategorias() // Obtener datos en un hilo de fondo
            withContext(Dispatchers.Main) { // Volver al hilo principal para actualizar la UI
                if (datos.isNotEmpty()) { // Verificar si hay datos antes de crear el adaptador
                    val adapter = AdaptadorMenu(datos)
                    recyclerView.adapter = adapter
                } else {
                    // Manejar el caso en que no hay datos, por ejemplo, mostrando un mensaje al usuario
                    Toast.makeText(this@MenuCategoriaActivity, "No se encontraron datos", Toast.LENGTH_SHORT).show()
                    println("No se encontraron datos")
                }
            }
        }
    }
}




