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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        val btn: Button = findViewById(R.id.btngoogle)
        btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandamos a traer a todos los elementos de la vista
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtContrasena)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)
        val btnGoogle = findViewById<Button>(R.id.btngoogle)
        val btnLogin_Repartidor = findViewById<Button>(R.id.btn_repartidor)
        val tvRecuperacionContrasena = findViewById<TextView>(R.id.tvRecuperacionContrasena)

        tvRecuperacionContrasena.setOnClickListener {
            val intent = Intent(this, RecuperacionContrasena::class.java)
            startActivity(intent)
        }

        btnGoogle.setOnClickListener {
            val pantallamenu = Intent(this, MainActivity::class.java)
            startActivity(pantallamenu)
        }

        btnLogin_Repartidor.setOnClickListener {
            val Intent = Intent(this, Login_Repartidor::class.java)
            startActivity(Intent)
        }

        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        btnEntrar.setOnClickListener {
            val txtCorreo = txtCorreo.text.toString()
            val txtContrasena = hashPassword(txtContrasena.text.toString())

            //traer el id cuando inicia sesion
            //Hago un select para ver guardar el rol del usuario
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()
                val resulSet =
                    objConexion?.prepareStatement("SELECT id_cliente FROM clientes_PTC WHERE correoElectronico = ?")!!
                resulSet.setString(1, txtCorreo)
                val resultado = resulSet.executeQuery()
                if (resultado.next()) {
                    //Lleno la variable global con el rol del usuario (dependiendo del correo)
                    idDelCliente = resultado.getString("id_cliente")
                    println("ESTE ES EL ROL DEL USUARIO: $idDelCliente")
                }
            }


            val pantallaprincipal = Intent(this, MainActivity::class.java)



            if (txtCorreo.isEmpty() || txtContrasena.isEmpty()){
                Toast.makeText(this, "Campos incompletos", Toast.LENGTH_SHORT).show()
            } else{
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().cadenaConexion()

                    val addAcceder = "select * from clientes_PTC where correoElectronico = ? AND contrasena = ?"
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
                            Toast.makeText(this@Login, "Contraseña o correo inválidos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

private fun hashPassword(password: String): String {
    val bytes = password.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}





