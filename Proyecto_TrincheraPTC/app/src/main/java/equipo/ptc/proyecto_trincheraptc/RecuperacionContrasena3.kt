package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class RecuperacionContrasena3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacion_contrasena3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreoNew= findViewById<EditText>(R.id.txtRecupContrasena)
        val btnCambiarContrasena = findViewById<Button>(R.id.btnRecupContinuar3)

        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }

        btnCambiarContrasena.setOnClickListener {
            val contraseña = txtCorreoNew.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                val updateClave = objConexion?.prepareStatement("update clientes_PTC set contrasena = ? where correoElectronico = ?")!!
                updateClave.setString(1, hashPassword(contraseña))
                updateClave.setString(2, RecuperacionContrasena.correo)
                updateClave.executeUpdate()
                val commit = objConexion.prepareStatement("commit")!!
                commit.executeUpdate()

                // Cambio a la pantalla de inicio de sesión después de 2 segundos
                withContext(Dispatchers.Main) {
                    txtCorreoNew.setText("")
                    delay(2000) // Retardo de 2 segundos
                    val intent = Intent(this@RecuperacionContrasena3, Login::class.java)
                    startActivity(intent)
                    finish() // Opcional, si deseas cerrar la actividad actual
                }
            }
        }
    }
}