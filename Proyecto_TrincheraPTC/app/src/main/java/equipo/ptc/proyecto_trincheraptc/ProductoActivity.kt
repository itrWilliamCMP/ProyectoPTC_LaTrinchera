package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.dataClassComida
import Modelo.tbMenuConProductos
import Modelo.tbProductos
import RecyclerViewHelpers.AdaptadorComidas
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_producto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val txtCantidad = findViewById<TextView>(R.id.txtCantidad)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        val TxtFalta = findViewById<TextView>(R.id.TxtFalta)
        val imgRegresar = findViewById<ImageView>(R.id.imgRegresar)
        val nombre = findViewById<TextView>(R.id.NombreCliente)
        val IMG = findViewById<ImageView>(R.id.ivImagenProducto)


        val id_menu = intent.getIntExtra("id_menu", 0)
        val categoria = intent.getStringExtra("categoria")
        val id_producto = intent.getIntExtra("id_producto", 0)
        val producto = intent.getStringExtra("producto")
        val descripcion = intent.getStringExtra("descripcion")
        val precioventa = intent.getIntExtra("precioventa", 0)
        val stock = intent.getIntExtra("stock", 0)
        val imagen_comida = intent.getStringExtra("imagen_comida")

        // txtProducto.text = producto
        txtCantidad.text = stock.toString()
        tvDescripcion.text = descripcion
        TxtFalta.text = precioventa.toString()
        nombre.text = producto
        IMG.setImageResource(resources.getIdentifier(imagen_comida, "drawable", packageName))


        suspend fun obtenerCategorias(id_producto: Int): List<tbMenuConProductos> {
            return withContext(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()
                if (objConexion == null) {
                    println("Error al establecer la conexión a la base de datos.")
                    return@withContext emptyList() // Devuelve una lista vacía si no hay conexión
                }

                val traerCosas = objConexion.prepareStatement("""
            SELECT
                dp.id_producto,
                dp.producto,
                dp.descripcion,
                dp.imagen_comida,
                dp.precioventa,
                dp.stock,
                c.categoria
            FROM
                Detalle_Productos_PTC dp
            INNER JOIN
                Menus_PTC c ON dp.ID_Menu = c.ID_Menu
            WHERE
                dp.id_producto = ?
        """)!!

                traerCosas.setInt(1, id_producto)
                val resultSet = traerCosas.executeQuery()
                val datos = mutableListOf<tbMenuConProductos>()

                while (resultSet.next()) {
                    // Extraer datos de resultSet
                }

                return@withContext datos
            }
        }



        CoroutineScope(Dispatchers.IO).launch {
            val centrosDB = obtenerCategorias(id_producto)

            withContext(Dispatchers.Main) {
                if (centrosDB.isNotEmpty()) {
                    val descripcion = centrosDB[0].descripcion
                    tvDescripcion.text = descripcion
                    println("ahhhhhhhhhhhhhhhhhhhhhhhhhhhh $descripcion")
                } else {
                    // Manejo de caso cuando no se encuentran categorías
                    println("No se encontraron categorías para el producto.")
                    tvDescripcion.text = "Descripción no disponible"
                }
            }


        } ?: run {
            println("No se recibió el producto en el intent.")
        }

        imgRegresar.setOnClickListener {
            val pantallaDetalleMenus = Intent(this, MenuCategoriaActivity::class.java)
            startActivity(pantallaDetalleMenus)
        }




    }
}