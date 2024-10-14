package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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

    private lateinit var etNombreUsuario: EditText
    private lateinit var etContrasena: EditText

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
        etNombreUsuario = findViewById(R.id.txtCorreo2) // Inicializamos aquí
        etContrasena = findViewById(R.id.txtContrasena2)     // Inicializamos aquí

        btnEntrar2.setOnClickListener {
            val nombreUsuario = etNombreUsuario.text.toString()
            val contrasena = etContrasena.text.toString()

            if (TextUtils.isEmpty(nombreUsuario)) {
                etNombreUsuario.error = "Ingrese el nombre de usuario"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(contrasena)) {
                etContrasena.error = "Ingrese la contraseña"
                return@setOnClickListener
            }

            validarCredenciales(nombreUsuario, contrasena)
        }
    }

    private fun validarCredenciales(nombreUsuario: String, contrasena: String) {
        if (nombreUsuario == "Trincherito" && contrasena == "trincherito14") {
            val intent = Intent(this@Login_Repartidor, MenuRepartidor::class.java)
            startActivity(intent)
            return
        }

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
                            etContrasena.error = "Contraseña incorrecta"
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Login_Repartidor, "Repartidor no encontrado", Toast.LENGTH_SHORT).show()
                        etNombreUsuario.error = "Repartidor no encontrado"
                    }
                }

                resultSet?.close()
                statement?.close()
                objConexion?.close()

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Login_Repartidor, "Error al validar credenciales: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}