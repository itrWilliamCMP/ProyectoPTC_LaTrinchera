package equipo.ptc.proyecto_trincheraptc

import ClasesPerfil.GetDatosClientes
import ClasesPerfil.SaveDatosClientes
import Modelo.Perfil_info
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class editar_perfil : AppCompatActivity() {

    // Declaración de variables
    private lateinit var textViewNombre: EditText
    private lateinit var textViewTelefono: EditText
    private lateinit var textViewCorreo: EditText
    private lateinit var textViewID: TextView
    private lateinit var btnGuardarPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        //Mandar a llamar los datos de la pantalla
        textViewNombre = findViewById(R.id.txtnombre_perfil_editar)
        textViewCorreo = findViewById(R.id.txtCorreo_perfil_editar)
        btnGuardarPerfil = findViewById(R.id.btn_guardar_perfil)

        // Obtener el ID del cliente del intent
        val id_cliente = intent.getIntExtra("id_cliente", -1)

        if (id_cliente != -1) {
            // Ejecutar la tarea en segundo plano usando coroutines
            getDatosCliente(id_cliente)
        } else {
            // Manejar el caso donde no se pasó un ID válido
            textViewNombre.setText("ID de cliente no válido")
            textViewTelefono.setText("")
            textViewCorreo.setText("")
            textViewID.text = ""
        }

        // Configurar el OnClickListener para el botón
        btnGuardarPerfil.setOnClickListener {
            updateDatosCliente(id_cliente);
        }


        // Configurar la imagen de retroceso
        val imgAtrasInfoPerfil = findViewById<ImageView>(R.id.imgAtrasInfoPerfil)

        imgAtrasInfoPerfil.setOnClickListener {
            finish()
        }
    }

    // Función para obtener los datos del cliente
    private fun getDatosCliente(clientId: Int) {
        lifecycleScope.launch {
            val client = withContext(Dispatchers.IO) {
                GetDatosClientes().getDatosCliente(clientId)
            }
            // Actualizar la interfaz con los datos obtenidos
            actualizarPantalla(client)
        }
    }

    // Función para actualizar los datos del cliente
    private fun updateDatosCliente(clientId: Int) {
        lifecycleScope.launch {
            // Llamar al método de actualización en un hilo en segundo plano
            val success = withContext(Dispatchers.IO) {
                SaveDatosClientes().updateDatosCliente(
                    clientId,
                    // Obtener los valores de los campos de texto
                    textViewNombre.text.toString(),
                    textViewTelefono.text.toString(),
                    textViewCorreo.text.toString()
                )
            }
            // Manejar el resultado de la actualización
            if (success) {
                // Mostrar mensaje de éxito o realizar acción adicional
                showToast("Datos actualizados exitosamente")
                val intent = Intent("ACTUALIZAR_DATOS")
                intent.putExtra("id_cliente", clientId)
                sendBroadcast(intent)
                finish()
            } else {
                // Mostrar mensaje de error o realizar acción adicional
                showToast("Error al actualizar los datos")
            }
        }
    }

    // Función para actualizar la interfaz con los datos del cliente
    private fun actualizarPantalla(client: Perfil_info?) {
        // Actualizar los campos de texto con los datos del cliente
        if (client != null) {
            textViewNombre.setText(client.nombre_clie)
            textViewTelefono.setText(client.telefono_clie)
            textViewCorreo.setText(client.correoElectronico)
            textViewID.setText(client.id_cliente.toString())

        } else {
            // Si el cliente es nulo, establecer valores predeterminados
            textViewNombre.setText("Cliente no encontrado")
            textViewTelefono.setText("")
            textViewCorreo.setText("")
            textViewID.setText("")
        }
    }

    // Función para mostrar mensajes usando Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}