package equipo.ptc.proyecto_trincheraptc

import ClasesPerfil.GetDatosClientes
import ClasesPerfil.SaveClaveCliente
import ClasesPerfil.SaveDireccionCliente
import Modelo.Perfil_info
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class direccion_entrega : AppCompatActivity() {

    // Declaración de variables
    private lateinit var txtDireccion:EditText
    private lateinit var btnGuardarDir: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_direccion_entrega)

        //Mandar a llamar los datos de la pantalla
       txtDireccion = findViewById(R.id.txtDireccion)
       btnGuardarDir = findViewById(R.id.btnGuardarDir)

        // Obtener el ID del cliente del intent
        val id_cliente = intent.getIntExtra("id_cliente", -1)

        // Verificar si se pasó un ID válido
        if (id_cliente == -1) {
            showToast("ID de cliente no válido")
            finish()
        }
        // Verificar si se pasó un ID válido

        if (id_cliente != -1) {
            // Ejecutar la tarea en segundo plano usando coroutines
            getDatosCliente(id_cliente)
        } else {
            showToast("ID de cliente no válido")
            finish()
        }


        // Configurar el OnClickListener para el botón
        btnGuardarDir.setOnClickListener {
            // Llamar a la función para actualizar la dirección de entrega
            updateDireccionCliente(id_cliente,txtDireccion.text.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar la imagen de retroceso
        val imgAtrasDireccion = findViewById<ImageView>(R.id.imgAtrasDireccion)

        imgAtrasDireccion.setOnClickListener {
            finish()
        }
    }

    // Función para actualizar la dirección de entrega
    private fun updateDireccionCliente(clientId: Int, direccion: String) {
        lifecycleScope.launch {
            // Llamar al método de actualización en un hilo en segundo plano

            // Manejar el resultado de la actualización
            val success = withContext(Dispatchers.IO) {
                SaveDireccionCliente().updateDireccionCliente(clientId, direccion)
            }
            // Manejar el resultado de la actualización
            if (success) {
                // Mostrar mensaje de éxito o realizar acción adicional
                showToast("Dirección de entrega actualizada exitosamente")
                finish()
            } else {
                // Mostrar mensaje de error o realizar acción adicional
                showToast("Error al actualizar la dirección de entrega")
            }

        }
    }

    // Función para mostrar mensajes usando Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Función para obtener los datos del cliente
    private fun getDatosCliente(clientId: Int) {
        lifecycleScope.launch {
            // Llamar al método de actualización en un hilo en segundo plano
            val client = withContext(Dispatchers.IO) {
                GetDatosClientes().getDatosCliente(clientId)
            }
            // Actualizar la interfaz con los datos obtenidos
            actualizarPantalla(client)
        }
    }

    // Función para actualizar la interfaz con los datos del cliente
    private fun actualizarPantalla(client: Perfil_info?) {
        if (client != null) {
            txtDireccion.setText(client.direccion_entrega)
        } else {
            txtDireccion.setText("")
        }
    }
}