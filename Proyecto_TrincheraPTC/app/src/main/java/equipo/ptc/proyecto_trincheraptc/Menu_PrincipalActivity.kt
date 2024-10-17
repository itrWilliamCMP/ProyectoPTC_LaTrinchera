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
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*

class Menu_PrincipalActivity : AppCompatActivity() {

    private lateinit var rcvComida: RecyclerView
    lateinit var tvNombreIngreso: TextView
    lateinit var imgPerfilMenuPrincipal: ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)
        setupWindowInsets()
        setupNavigation()

        // Inicializamos las vistas
        rcvComida = findViewById(R.id.rvComidaCategoria)
        tvNombreIngreso = findViewById(R.id.tvNombreIngreso)
        imgPerfilMenuPrincipal = findViewById(R.id.imgPerfilMenuPrincipal)

        // Configuramos el layout del RecyclerView
        rcvComida.layoutManager = GridLayoutManager(this, 2)



        // Cargar nombre e imagen
        GlobalScope.launch(Dispatchers.Main) {
            tvNombreIngreso.text = traerNombre()
        }

        GlobalScope.launch(Dispatchers.Main) {
            val imageUrl = traerImagen()
            cargarImagenPerfil(imageUrl)
        }



        // Obtener los datos del menú
        fetchData()
    }

    // Función para obtener la URL de la imagen de Firebase Storage
    suspend fun traerImagen(): String? {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val query = objConexion?.prepareStatement(
                "SELECT imagen_clientes FROM Clientes_PTC WHERE correoElectronico = ?"
            )
            query?.setString(1, Login.correoDelCliente)
            val resultSet = query?.executeQuery()

            var imagenUrl: String? = null
            if (resultSet != null && resultSet.next()) {
                imagenUrl = resultSet.getString("imagen_clientes") // URL de la imagen guardada en Firebase
            }

            resultSet?.close()
            query?.close()
            objConexion?.close()

            return@withContext imagenUrl
        }
    }

    // Función para cargar el nombre desde la base de datos
    suspend fun traerNombre(): String? {
        return withContext(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            val query = objConexion?.prepareStatement(
                "SELECT nombre_clie FROM Clientes_PTC WHERE correoElectronico = ?"
            )
            query?.setString(1, Login.correoDelCliente)
            val resultSet = query?.executeQuery()

            var nombre: String? = null
            if (resultSet != null && resultSet.next()) {
                nombre = resultSet.getString("nombre_clie")
            }

            resultSet?.close()
            query?.close()
            objConexion?.close()

            return@withContext nombre
        }
    }

    // Función para cargar la imagen de perfil usando Glide
    private fun cargarImagenPerfil(imagenUrl: String?) {
        if (!imagenUrl.isNullOrEmpty()) {
            // Obtener la referencia de Firebase Storage
            val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imagenUrl)

            // Obtener la URL de descarga y cargarla en el ImageView usando Glide
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imgPerfilMenuPrincipal)
            }.addOnFailureListener {
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No se encontró la URL de la imagen", Toast.LENGTH_SHORT).show()
        }
    }

    // Configuración de WindowInsets para las barras del sistema
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Configuración de la navegación en el menú
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

    // Función para obtener los datos de la base de datos
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


