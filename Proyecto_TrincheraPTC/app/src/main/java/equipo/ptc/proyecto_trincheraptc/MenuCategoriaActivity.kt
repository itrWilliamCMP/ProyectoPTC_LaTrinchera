package equipo.ptc.proyecto_trincheraptc


import Modelo.ClaseConexion
import Modelo.MenuComidas
import Modelo.tbMenu
import Modelo.tbProductos
import RecyclerViewHelpers.AdaptadorMenuCategorias
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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

                val valoresjuntos = tbProductos(id_producto, id_menu, producto, descripcion, precioventa, stock)

                Datos.add(valoresjuntos)
            }
            return Datos


//        val adapter = AdaptadorMenuCategorias(comidas)
//        recyclerView.adapter = adapter

            val imgBackSopas = findViewById<ImageView>(R.id.imgBackSopas)
            imgBackSopas.setOnClickListener {
                val pantallaLogin = Intent(this, Menu_PrincipalActivity::class.java)
                startActivity(pantallaLogin)
            }
        }
    }
}