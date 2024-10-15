package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class Login : AppCompatActivity() {
    companion object variablesLogin {
        lateinit var idDelCliente: String
        lateinit var correoDelCliente: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnLogin_Repartidor = findViewById<Button>(R.id.btn_repartidor)
        val tvRecuperacionContrasena = findViewById<TextView>(R.id.tvRecuperacionContrasena)

        // Redireccionar al activity de recuperación de contraseña
        tvRecuperacionContrasena.setOnClickListener {
            val intent = Intent(this, RecuperacionContrasena::class.java)
            startActivity(intent)
        }



        // Redireccionar a la pantalla de Login para repartidores
        btnLogin_Repartidor.setOnClickListener {
            val intent = Intent(this, Login_Repartidor::class.java)
            startActivity(intent)
        }

        // Redireccionar a la pantalla de registro
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        // Funcionalidad del botón Entrar (login)
        btnEntrar.setOnClickListener {

            correoDelCliente = txtCorreo.text.toString()
            val txtCorreo = txtCorreo.text.toString()
            val txtContrasena = hashPassword(txtContrasena.text.toString())

            // Traer el id del cliente al iniciar sesión
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()
                val resulSet =
                    objConexion?.prepareStatement("SELECT id_cliente FROM clientes_PTC WHERE correoElectronico = ?")!!
                resulSet.setString(1, txtCorreo)
                val resultado = resulSet.executeQuery()
                if (resultado.next()) {
                    idDelCliente = resultado.getString("id_cliente")
                    println("ID del cliente: $idDelCliente")
                }
            }

            val pantallaprincipal = Intent(this, MainActivity::class.java)

            if (txtCorreo.isEmpty() || txtContrasena.isEmpty()) {
                Toast.makeText(this, "Campos incompletos", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().cadenaConexion()

                    val addAcceder =
                        "select * from clientes_PTC where correoElectronico = ? AND contrasena = ?"
                    val objAcceder = objConexion?.prepareStatement(addAcceder)
                    objAcceder?.setString(1, txtCorreo)
                    objAcceder?.setString(2, txtContrasena)

                    val resultado = objAcceder?.executeQuery()

                    if (resultado?.next() == true) {
                        withContext(Dispatchers.IO) {
                            startActivity(pantallaprincipal)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Login,
                                "Contraseña o correo inválidos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    // Función para hashear la contraseña
    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}
