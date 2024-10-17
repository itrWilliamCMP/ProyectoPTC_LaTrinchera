package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.dataClassComida
import Modelo.tbMenu
import Modelo.tbMenuConProductos
import RecyclerViewHelpers.AdaptadorCategoriaMenu
import RecyclerViewHelpers.AdaptadorComidas
import RecyclerViewHelpers.AdaptadorDetalleMenu
import RecyclerViewHelpers.AdaptadorMenu
import RecyclerViewHelpers.AdaptadorMenuCategorias
import android.annotation.SuppressLint
import android.content.Intent
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
import com.bumptech.glide.Glide
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

        val back = findViewById<ImageView>(R.id.imgBack)
        back.setOnClickListener {
            val pantallaMenuPricipal = Intent(this, Menu_PrincipalActivity::class.java)
            startActivity(pantallaMenuPricipal)
        }


        val recyclerView: RecyclerView = findViewById(R.id.rvMenuCategoria)
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*
         id_menu = intent.getIntExtra("id_menu", 0).toString()
        println("este es el IDDD del taco $id_menu")
         categoria = intent.getStringExtra("categoria").toString()
         id_producto = intent.getIntExtra("id_producto", 0).toString()
        val producto = intent.getStringExtra("producto")
        val descripcion = intent.getStringExtra("descripcion")
        val precioventa = intent.getIntExtra("precioventa", 0)
        val stock = intent.getIntExtra("stock", 0)
         imagen_categoria = intent.getStringExtra("imagen_categoria").toString()
         nombre_categoria = intent.getStringExtra("nombre_categoria").toString()

         */


        val tvNomCategoria = findViewById<TextView>(R.id.tvNomCategoria)
        tvNomCategoria.text = AdaptadorMenu.varGlobaAdap.categoria

        val imgCat = findViewById<ImageView>(R.id.imgCategoria)
        Glide.with(imgCat.context).
        load(AdaptadorMenu.imagen_categoria).
        into(imgCat)

       // val rvMenuCategoria = findViewById<RecyclerView>(R.id.rvMenuCategoria)
        //rvMenuCategoria.layoutManager = LinearLayoutManager(this)
        // Verifica que la categoría no sea nula antes de llamar a obtenerCategorias
        AdaptadorMenu.categoria?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val centrosDB = obtenerCategorias(AdaptadorMenu.id_menu) // Pasar el valor de categoría
                withContext(Dispatchers.Main) {
                    val miAdapter = AdaptadorComidas(centrosDB)
                    recyclerView.adapter = miAdapter
                }
            }
        } ?: run {
            println("No se recibió la categoría en el intent.")
        }
    }

    suspend fun obtenerCategorias(ID_Menu: String): List<dataClassComida> {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val traerCosas = objConexion?.prepareStatement("""
SELECT     
    dp.Producto,
    dp.Imagen_Comida,
    c.Categoria,
    c.Imagen_categoria 
FROM
    Detalle_Productos_PTC dp
INNER JOIN
    Menus_PTC c ON dp.ID_Menu = c.ID_Menu
WHERE
    c.ID_Menu = ?
                    """)!!
            traerCosas.setInt(1, ID_Menu.toString().toInt()) // Establecer el parámetro de categoría
            val resultSet = traerCosas.executeQuery()
            val datos = mutableListOf<dataClassComida>()
            if (resultSet != null) {
                while (resultSet.next()) {
                    val producto = resultSet.getString("Producto")
                    val imagen_comida = resultSet.getString("Imagen_Comida")
                    val valoresjuntos = dataClassComida(producto, imagen_comida, ID_Menu.toString().toInt())
                    datos.add(valoresjuntos)
                }
            } else {
                println("La consulta no devolvió resultados.")
            }
            return@withContext datos;
        }
    }
}