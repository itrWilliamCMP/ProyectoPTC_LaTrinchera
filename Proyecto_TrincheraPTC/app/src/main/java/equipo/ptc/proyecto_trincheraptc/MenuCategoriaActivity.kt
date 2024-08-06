package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbProductos
import RecyclerViewHelpers.AdaptadorDetalleMenu
import android.os.Bundle
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

      //
        val id_menu = intent.getIntExtra("id_menu", 0)
//        val id_producto = intent.getIntExtra("id_producto", 0)
//        val id_menu = intent.getIntExtra("id_menu", 0)
//        val producto = intent.getStringExtra("producto")
//        val descripcion = intent.getStringExtra("descripcion")
//        val precioventa = intent.getIntExtra("precioventa",0 )
//        val stock = intent.getIntExtra("stock",0)
//
//        val txtNombreProducto = findViewById<TextView>(R.id.tvNombreProducto)
//
//        txtNombreProducto.text = producto


        //
        fun obtenerCategorias(): List<tbProductos> {
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM Productos_PTC")!!
            val Datos = mutableListOf<tbProductos>()

            while (resultSet.next()) {
                val id_producto = resultSet.getInt("id_producto")
                val id_menu = resultSet.getInt("id_menu")
                val producto = resultSet.getString("producto")
                val descripcion = resultSet.getString("descripcion")
                val precioventa = resultSet.getInt("precioventa")
                val stock = resultSet.getInt("stock")
                val imagen_categoria = resultSet.getString("imagen_categoria")
                val imagen_producto = resultSet.getString("imagen_producto")
                val valoresjuntos = tbProductos(id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_categoria,imagen_producto)

                Datos.add(valoresjuntos)
            }
            return Datos
//        val adapter = AdaptadorMenuCategorias(comidas)
//        recyclerView.adapter = adapter

        }
        CoroutineScope(Dispatchers.IO).launch {
            val Datos = obtenerCategorias()
            withContext(Dispatchers.Main) {
                val adapter = AdaptadorDetalleMenu(Datos)
                recyclerView.adapter = adapter
            }
        }
    }
}

