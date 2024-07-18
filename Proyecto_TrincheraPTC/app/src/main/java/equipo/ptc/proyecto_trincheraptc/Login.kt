package equipo.ptc.proyecto_trincheraptc

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandamos a traer a todos los elementos de la vista
        val txtCorreoElectronico = findViewById<EditText>(R.id.txtCorreoElectronico)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnGoogle = findViewById<Button>(R.id.btngoogle)

        if (txtCorreoElectronico == null || txtContrasena == null || btnEntrar == null || btnRegistrarse == null || btnGoogle == null) {
            Log.e("Login", "Uno o más elementos no se han ingresado como se debe")
            return
        }

        btnRegistrarse.setOnClickListener {
            val intent1 = Intent(this, Register::class.java)
            startActivity(intent1)
        }

        //Botón para ir a la pantalla inicial (Mientras no haya registro con google)
        btnGoogle.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //2- Programar los botones
        btnEntrar.setOnClickListener {
            // obtengo los valores ingresados por el usuario.
            val correo = txtCorreoElectronico.text.toString()
            val contrasena = txtContrasena.text.toString()

            if (!correo.matches(".@.".toRegex())) {
                Toast.makeText(this, "Ingrese correo valido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación: Que la contraseña contenga entre 6 y 24 caracteres.
            if (contrasena.length < 6 || contrasena.length > 24) {
                Toast.makeText(
                    this,
                    "Ingrese una clave entre 6 y 24 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            fun hashSHA256(input: String): String {
                val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
                return bytes.joinToString("") { "%02x".format(it) }
            }

            // Se programa los botones
            val pantallaPrincipal = Intent(this, MainActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()

                //Verificación de error en la conexión a la BD
                if (objConexion == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Login,
                            "Usuario o contraseña incorrecta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                val contrasenaEncriptada = hashSHA256(txtContrasena.text.toString())
                val verificarUsuario = objConexion.prepareStatement("SELECT * FROM clientes_PTC WHERE correoElectronico = ? AND contrasena = ?")
                verificarUsuario.setString(1, txtCorreoElectronico.text.toString())
                verificarUsuario.setString(2, contrasenaEncriptada)
                val resultado = verificarUsuario.executeQuery()

                if (resultado.next()) {
                    withContext(Dispatchers.Main) {
                        startActivity(pantallaPrincipal)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Login,
                            "Usuario o contraseña están incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                        println("contraseña $contrasenaEncriptada")
                    }
                }
            }
        }
    }
}
