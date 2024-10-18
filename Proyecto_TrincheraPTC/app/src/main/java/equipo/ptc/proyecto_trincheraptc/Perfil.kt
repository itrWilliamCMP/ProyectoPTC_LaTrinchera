package equipo.ptc.proyecto_trincheraptc

import ClasesPerfil.GetDatosClientes
import Modelo.ClaseConexion
import Modelo.Perfil_info
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Perfil : AppCompatActivity() {

    // Declaración de variables
    private lateinit var txtNomUsuarioPerfil: TextView
    private lateinit var txtCorreoPerfil: TextView
    lateinit var imgPerfilMenuPrincipal: ImageView

    // Variable para almacenar el ID del cliente actual

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idtraido = Login.idDelCliente

        // Obtener el ID del cliente del intent
        val id_cliente = intent.getIntExtra("id_cliente", idtraido.toString().toInt())

        imgPerfilMenuPrincipal = findViewById(R.id.fotoperfiiil)

        GlobalScope.launch(Dispatchers.Main) {
            val imageUrl = traerImagen()
            cargarImagenPerfil(imageUrl)
        }




        if (id_cliente != -1) {
            // Obtener los datos del cliente usando corrutinas
            getDatosCliente(id_cliente)
        } else {
            // Finalizar la actividad si no hay ID
            showToast("No se ha obtenido el ID del cliente") // Mostrar mensaje de error
            finish()

        }

        // Mandar a llamar los valores de las imagenes
        val imgCambioDeContrasena = findViewById<ImageView>(R.id.imgCambioDeContrasena)
        val imgInformacionPersonal = findViewById<ImageView>(R.id.imgInformacion_personal)
        val imgDireccionEntrega = findViewById<ImageView>(R.id.imgDireccion_entrega)
        val SalirLogin = findViewById<ImageView>(R.id.SalirLogin)

        // Mandar a llamar los valores de los textview
        txtNomUsuarioPerfil = findViewById<TextView>(R.id.txtNomUsuarioPerfil)
        txtCorreoPerfil = findViewById<TextView>(R.id.txtCorreoPerfil)

        val BackLog = findViewById<ImageView>(R.id.SalirLogin)
        BackLog.setOnClickListener {
            val Login = Intent(this, Login::class.java)
            startActivity(Login)
        }

        // Manejar clic en Información Personal
        imgInformacionPersonal.setOnClickListener {
            val intent = Intent(this, informacion_perfil::class.java)
            // Pasar el ID del cliente a la siguiente actividad
            intent.putExtra("id_cliente", id_cliente)
            editarPerfilLauncher.launch(intent) // Lanzar actividad y esperar resultado
        }



        // Manejar clic en Cambio de Contraseña
        imgCambioDeContrasena.setOnClickListener {
            val intent = Intent(this, cambio_clave::class.java)
            // Pasar el ID del cliente a la siguiente actividad
            intent.putExtra("id_cliente", id_cliente)
            startActivity(intent) // Iniciar actividad
        }



        // Manejar clic en Dirección de Entrega
        imgDireccionEntrega.setOnClickListener {
            val intent = Intent(this, direccion_entrega::class.java)
            // Pasar el ID del cliente a la siguiente actividad
            intent.putExtra("id_cliente", id_cliente)
            startActivity(intent) // Iniciar actividad
        }

        //-------------------------------------------------------------------------------------------
        // Botones de navegación
        //-------------------------------------------------------------------------------------------
        val imgMenuOutline = findViewById<ImageView>(R.id.imgMenuOutline)
        imgMenuOutline.setOnClickListener {
            val pantallaLogin = Intent(this, Menu_PrincipalActivity::class.java)
            startActivity(pantallaLogin)
        }

        val imgPrincipalOutline = findViewById<ImageView>(R.id.imgPrincipalOutline)
        imgPrincipalOutline.setOnClickListener {
            val pantallaLogin = Intent(this, MainActivity::class.java)
            startActivity(pantallaLogin)
        }

        val imgCarrito = findViewById<ImageView>(R.id.imgCarritoOutline)
        imgCarrito.setOnClickListener {
            val pantallaLogin = Intent(this, CarroCompras::class.java)
            startActivity(pantallaLogin)
        }
        //-------------------------------------------------------------------------------------------
    }

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






    // Manejar el resultado de la actividad editar perfil
    //registra un lanzador de actividad que se utiliza para iniciar otra actividad y recibir un resultado de vuelta.
    private val editarPerfilLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Actualizar datos después de editar
        val idtraido2 = Login.idDelCliente
        getDatosCliente(idtraido2.toString().toInt())
    }

    // Obtener datos del cliente usando corrutinas
    private fun getDatosCliente(clientId: Int) {
        lifecycleScope.launch {
            val client = withContext(Dispatchers.IO) {
                GetDatosClientes().getDatosCliente(clientId) // Obtener datos en segundo plano
            }
            actualizarPantalla(client) // Actualizar la interfazcon los datos obtenidos
        }
    }

    // Actualizar la pantalla con los datos del cliente
    private fun actualizarPantalla(client: Perfil_info?) {
        if (client != null) {
            txtNomUsuarioPerfil.text = client.nombre_clie
            txtCorreoPerfil.text = client.correoElectronico
        } else {
            txtNomUsuarioPerfil.text = "CLIENTE NO ENCONTRADO" // Mensaje si no se encuentra el cliente
            txtCorreoPerfil.text = ""
        }
    }

    // Función para mostrar mensajes usando Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}