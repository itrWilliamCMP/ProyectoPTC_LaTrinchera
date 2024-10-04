package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenuConProductos
import RecyclerViewHelpers.AdaptadorMenu
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Menu_PrincipalActivity : AppCompatActivity() {

    private lateinit var rcvComida: RecyclerView

    lateinit var nombreTT:String

   lateinit var tvNombreIngreso: TextView

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
        tvNombreIngreso = findViewById(R.id.tvNombreIngreso)

        tvNombreIngreso.text = Login


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
            val datos = obtenerMenusConClientes()
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

    private fun obtenerMenusConClientes(): List<tbMenuConProductos> {
        val objConexion = ClaseConexion().cadenaConexion()
        val statement = objConexion?.createStatement()
        val resultSet = statement?.executeQuery("SELECT id_menu, categoria, imagen_categoria FROM Menus_PTC")

        val datos = mutableListOf<tbMenuConProductos>()

        resultSet?.let {
            while (it.next()) {
                val categoriaMenu = tbMenuConProductos(
                    it.getInt("id_menu"),
                    it.getString("categoria"),
                    0,  // id_producto (dummy value, since it's not in the query)
                    "", // producto (dummy value)
                    "", // descripcion (dummy value)
                    0.0, // precioventa (dummy value)
                    0,  // stock (dummy value)
                    it.getString("imagen_categoria"),
                    ""  // imagen_comida (dummy value)
                )
                datos.add(categoriaMenu)
            }
        } ?: run {
            println("La consulta no devolvió resultados.")
        }

        return datos
    }



}




}