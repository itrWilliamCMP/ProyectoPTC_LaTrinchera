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
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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
            Log.e("Login", "Uno o más elementos de la interfaz no se encuentran en el layout.")
            return
            }

        fun hashSHA256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }

        //2- Se programa los botones
        btnEntrar.setOnClickListener {


            val pantallaPrincipal = Intent(this, MainActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                //2- Creo una variable que contenga un PrepareStatement
                //Se hace un select where el correo y la contraseña sean iguales a
                //los que el usuario escribe
                //Si el select encuentra un resultado es por que el usuario y contraseña si están
                //en la base de datos, si se equivoca al escribir algo, no encontrará nada el select
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

                val verificarUsuario = objConexion?.prepareStatement("SELECT * FROM clientes_PTC WHERE correoElectronico = ? AND contrasena = ?")!!
                verificarUsuario.setString(1, txtCorreoElectronico.text.toString())
                verificarUsuario.setString(2, contrasenaEncriptada)
                verificarUsuario.executeUpdate()
                val resultado = verificarUsuario.executeQuery()
                if (resultado.next()) {
                    startActivity(pantallaPrincipal)
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Login,
                            "Usuario o contraseña estan incorrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                        println("contraseña $contrasenaEncriptada")
                    }

                }
            }
        }

        btnRegistrarse.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        //Botón para ir a la pantalla inicial (Mientras no haya registro con google)
        val btn : Button = findViewById(R.id.btngoogle)
        btn.setOnClickListener {
            val Intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }
    }
}

