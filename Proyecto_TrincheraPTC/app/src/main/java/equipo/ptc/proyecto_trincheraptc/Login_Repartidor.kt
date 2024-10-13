package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login_Repartidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_repartidor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val BackLog = findViewById<ImageView>(R.id.BackLog)
        BackLog.setOnClickListener {
            val Login = Intent(this, Login::class.java)
            startActivity(Login)
        }

        val btnEntrar2 = findViewById<Button>(R.id.btnEntrar2)
        val etNombreUsuario = findViewById<EditText>(R.id.txtCorreo2)
        val etContrasena = findViewById<EditText>(R.id.txtContrasena2)

        btnEntrar2.setOnClickListener {
            val nombreUsuario = etNombreUsuario.text.toString()
            val contrasena = etContrasena.text.toString()
            validarCredenciales(nombreUsuario, contrasena)
        }
    }
//Nombre Trincherito
    //Contraseña trincheritos14
    private fun validarCredenciales(nombreUsuario: String, contrasena: String) {
        if (nombreUsuario == "Trincherito" && contrasena == "trincheritos14") {
            // Credenciales correctas, iniciar la actividad MenuRepartidor
            val intent = Intent(this@Login_Repartidor, MenuRepartidor::class.java)
            startActivity(intent)
        } else {
            // Validar con la base de datos si las credenciales no coinciden con las predefinidas
            validarCredencialesEnBD(nombreUsuario, contrasena)
        }
    }

    private fun validarCredencialesEnBD(nombreUsuario: String, contrasena: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val objConexion = ClaseConexion().cadenaConexion()
                val statement = objConexion?.prepareStatement(
                    "SELECT contrasena FROM Empleados_PTC WHERE nom_empleado = ?"
                )
                statement?.setString(1, nombreUsuario)
                val resultSet = statement?.executeQuery()

                if (resultSet?.next() == true) {
                    val contrasenaAlmacenada = resultSet.getString("contrasena")
                    if (contrasena == contrasenaAlmacenada) {
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@Login_Repartidor, MenuRepartidor::class.java)
                            startActivity(intent)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Login_Repartidor, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Login_Repartidor, "Repartidor no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Login_Repartidor, "Error al validar credenciales: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}