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

        val txtProducto = findViewById<TextView>(R.id.txtProducto)
        val txtCantidad = findViewById<TextView>(R.id.txtCantidad)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcion)
        val TxtFalta = findViewById<TextView>(R.id.TxtFalta)
        val imgRegresar = findViewById<ImageView>(R.id.imgRegresar)

        val id_menu = intent.getIntExtra("id_menu", 0)
        val categoria = intent.getStringExtra("categoria")
        val id_producto = intent.getIntExtra("id_producto", 0)
        val producto = intent.getStringExtra("producto")
        val descripcion = intent.getStringExtra("descripcion")
        val precioventa = intent.getIntExtra("precioventa", 0)
        val stock = intent.getIntExtra("stock", 0)

        txtProducto.text = producto
        txtCantidad.text = stock.toString()
        tvDescripcion.text = descripcion
        TxtFalta.text = precioventa.toString()

        suspend fun obtenerCategorias(id_producto: Int): List<tbMenuConProductos> {
            return withContext(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()
                val traerCosas = objConexion?.prepareStatement("""
SELECT
    dp.id_producto,
    dp.producto,
    dp.imagen_Comida,
    dp.descripcion,
    dp.imagen_comida,
    dp.precioventa,
    dp.stock
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
                if (resultSet != null) {
                    while (resultSet.next()) {
                        val id_menu = resultSet.getInt("id_menu")
                        val categoria = resultSet.getString("categoria")
                        val id_producto = resultSet.getInt("id_producto")
                        val producto = resultSet.getString("producto")
                        val descripcion = resultSet.getString("descripcion")
                        val precioventa = resultSet.getInt("precioventa")
                        val stock = resultSet.getInt("stock")
                        val imagen_categoria = resultSet.getString("imagen_categoria")
                        val imagen_comida = resultSet.getString("imagen_comida")
                        val valoresjuntos = tbMenuConProductos(id_menu, categoria, id_producto, producto, descripcion, precioventa, stock, imagen_categoria, imagen_comida)
                        datos.add(valoresjuntos)
                    }
                } else {
                    println("La consulta no devolvió resultados.")
                }
                return@withContext datos;
            }
            }
        categoria?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val centrosDB = obtenerCategorias(id_producto)
                withContext(Dispatchers.Main) {
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