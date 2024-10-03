package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenuConProductos
import RecyclerViewHelpers.AdaptadorMenu
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Menu_PrincipalActivity : AppCompatActivity() {

    private lateinit var rcvComida: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)
        setupWindowInsets()

        setupNavigation()

        rcvComida = findViewById(R.id.rvComidaCategoria)
        rcvComida.layoutManager = GridLayoutManager(this, 2)

        fetchData()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupNavigation() {
        findViewById<ImageView>(R.id.imgCarritoOutline).setOnClickListener {
            startActivity(Intent(this, CarroCompras::class.java))
        }
        findViewById<ImageView>(R.id.imgPrincipalOutline).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<ImageView>(R.id.imgPerfilOutline).setOnClickListener {
            startActivity(Intent(this, Perfil::class.java))
        }
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            val datos = obtenerCategoriasConProductos()
            withContext(Dispatchers.Main) {
                if (datos.isNotEmpty()) {
                    val adapter = AdaptadorMenu(datos)
                    rcvComida.adapter = adapter
                } else {
                    Toast.makeText(this@Menu_PrincipalActivity, "No se encontraron datos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun obtenerCategoriasConProductos(): List<tbMenuConProductos> {
        val objConexion = ClaseConexion().cadenaConexion()
        val statement = objConexion?.createStatement()
        val resultSet = statement?.executeQuery("SELECT Menus_PTC.id_menu, Menus_PTC.categoria, Detalle_Productos_PTC.id_producto, Detalle_Productos_PTC.producto, Detalle_Productos_PTC.descripcion, Detalle_Productos_PTC.precioventa, Detalle_Productos_PTC.stock, Menus_PTC.imagen_categoria, Detalle_Productos_PTC.imagen_comida FROM Menus_PTC INNER JOIN Detalle_Productos_PTC ON Menus_PTC.id_menu = Detalle_Productos_PTC.id_menu")

        val datos = mutableListOf<tbMenuConProductos>()

        resultSet?.let {
            while (it.next()) {
                val valoresjuntos = tbMenuConProductos(
                    it.getInt("id_menu"),
                    it.getString("categoria"),
                    it.getInt("id_producto"),
                    it.getString("producto"),
                    it.getString("descripcion"),
                    it.getDouble("precioventa"),
                    it.getInt("stock"),
                    it.getString("imagen_categoria"),
                    it.getString("imagen_comida")
                )
                datos.add(valoresjuntos)
            }
        } ?: run {
            println("La consulta no devolvió resultados.")
        }

        return datos
    }
}