package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import Modelo.tbMenu
import RecyclerViewHelpers.AdaptadorMenu
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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
    override fun onCreate(savedInstanceState: Bundle?) {
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

        val rcvComida = findViewById<RecyclerView>(R.id.rvComida)

        rcvComida.layoutManager = LinearLayoutManager(this)

        fun obtenerCategorias(): List<tbMenu> {
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM Menus_PTC")!!

            val Datos = mutableListOf<tbMenu>()

            while (resultSet.next()) {
                val id_menu = resultSet.getInt("id_menu")
                val categoria = resultSet.getString("categoria")

                val valoresjuntos = tbMenu(id_menu, categoria)

                Datos.add(valoresjuntos)
            }
            return Datos
        }
        CoroutineScope(Dispatchers.IO).launch {
            val Datos = obtenerCategorias()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorMenu(Datos)
                rcvComida.adapter = adapter
            }

        }
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
    }
}
