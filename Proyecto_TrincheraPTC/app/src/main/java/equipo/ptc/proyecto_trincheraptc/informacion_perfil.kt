package equipo.ptc.proyecto_trincheraptc

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ClasesPerfil.GetDatosClientes
import Modelo.Perfil_info
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class informacion_perfil : AppCompatActivity() {

    // Declaración de variables
    private lateinit var textViewNombre: TextView
    private lateinit var textViewTelefono: TextView
    private lateinit var textViewCorreo: TextView
    private lateinit var textViewID: TextView
    private lateinit var btnEditarPerfil: Button

    // Variable para almacenar el ID del cliente actual
    private var currentClientId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_perfil)

        //Mandar a llamar los datos de la pantalla
        textViewNombre = findViewById(R.id.txtnombre_perfil)
        textViewTelefono = findViewById(R.id.txttelefono)
        textViewCorreo = findViewById(R.id.txtCorreo_perfil)
        textViewID = findViewById(R.id.txtID)
        btnEditarPerfil = findViewById(R.id.btn_editarperfil)

        // Obtener el ID del cliente del intent
        val id_cliente = intent.getIntExtra("id_cliente", -1)
        // Guardar el ID del cliente actual
        currentClientId = id_cliente

        // Verificar si se pasó un ID válido
        if (id_cliente != -1) {
            // Ejecutar la tarea en segundo plano usando coroutines
            getDatosCliente(id_cliente)
        } else {
            // Manejar el caso donde no se pasó un ID válido
            textViewNombre.text = "ID de cliente no válido"
            textViewTelefono.text = ""
            textViewCorreo.text = ""
            textViewID.text = ""
        }



        // Configurar la imagen de retroceso
        val imgAtras_perfil = findViewById<ImageView>(R.id.imgAtras_perfil)
        imgAtras_perfil.setOnClickListener {
            val intent = Intent(this, Perfil::class.java)
            intent.putExtra("id_cliente", id_cliente)
            startActivity(intent)

        }



        // Configurar el OnClickListener para el botón
        btnEditarPerfil.setOnClickListener {
            val intent = Intent(this, editar_perfil::class.java)
            intent.putExtra("id_cliente", id_cliente)

            // Lanzar la actividad de edición de perfil
            editarPerfilLauncher.launch(intent) // abro la activity a traves de este metodo para que me actualice los datos cuando regrese
        }
    }


    // Manejar el resultado de la actividad editar perfil
    private val editarPerfilLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Cuando regresas de la actividad de edición, usa currentClientId para actualizar los datos
        getDatosCliente(currentClientId)
    }

    // Función para obtener los datos del cliente
    private fun getDatosCliente(clientId: Int) {
        lifecycleScope.launch {
            // Llamar al método de actualización en un hilo en segundo plano
            val client = withContext(Dispatchers.IO) {
                // Obtener los datos del cliente usando GetDatosClientes
                GetDatosClientes().getDatosCliente(clientId)
            }
            // Actualizar la interfaz con los datos obtenidos
            actualizarPantalla(client)
        }
    }

    // Función para actualizar la interfaz con los datos del cliente
    private fun actualizarPantalla(client: Perfil_info?) {
        // Actualizar los campos de texto con los datos del cliente
        if (client != null) {
            textViewNombre.text = client.nombre_clie
            textViewTelefono.text = client.telefono_clie
            textViewCorreo.text = client.correoElectronico
            textViewID.text = client.id_cliente.toString()

            // Actualizar los campos de texto con los datos del cliente

        } else {
            // Si el cliente es nulo, establecer valores predeterminados
            textViewNombre.text = "Cliente no encontrado"
            textViewTelefono.text = ""
            textViewCorreo.text = ""
            textViewID.text = ""
        }
    }

}