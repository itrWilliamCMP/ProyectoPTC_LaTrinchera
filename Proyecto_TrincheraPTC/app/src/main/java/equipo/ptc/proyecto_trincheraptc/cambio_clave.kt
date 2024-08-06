package equipo.ptc.proyecto_trincheraptc

import ClasesPerfil.SaveClaveCliente
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

class cambio_clave : AppCompatActivity() {

    // Declaración de variables
    private lateinit var txtClave1: EditText
    private lateinit var txtClave2: EditText
    private lateinit var btnGuardarClave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cambio_clave)

        //Mandar a llamar los datos de la pantalla
        txtClave1 = findViewById(R.id.txtClave1)
        txtClave2 = findViewById(R.id.txtClave2)
        btnGuardarClave = findViewById(R.id.btnGuardarClave)

        // Obtener el ID del cliente del intent
        val id_cliente = intent.getIntExtra("id_cliente", -1)

        // Verificar si el ID es válido
        if (id_cliente == -1) {
            showToast("ID de cliente no válido")
            finish()
        }

        // Configurar el OnClickListener para el botón
        btnGuardarClave.setOnClickListener {
            updateClaveCliente(id_cliente,txtClave1.text.toString(), txtClave2.text.toString());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar la imagen de retroceso
        val imgAtrasCambioClave = findViewById<ImageView>(R.id.imgAtrasCambioClave)
        imgAtrasCambioClave.setOnClickListener {
           finish()
        }
    }


    // Función para actualizar la contraseña
    private fun updateClaveCliente(clientId: Int, clave1: String, clave2: String) {
        lifecycleScope.launch {
            // Llamar al método de actualización en un hilo en segundo plano

            if (clave1 != clave2) {
                showToast("Las claves no coinciden")
            }else{

                val success = withContext(Dispatchers.IO) {
                    SaveClaveCliente().updateClaveCliente(clientId, clave1, clave2)
                }
                // Manejar el resultado de la actualización
                if (success) {
                    // Mostrar mensaje de éxito o realizar acción adicional
                    showToast("Clave actualizada exitosamente")
                    finish()
                } else {
                    // Mostrar mensaje de error o realizar acción adicional
                    showToast("Error al actualizar los datos")
                }
            }
        }




    }



    // Función para mostrar mensajes usando Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



}