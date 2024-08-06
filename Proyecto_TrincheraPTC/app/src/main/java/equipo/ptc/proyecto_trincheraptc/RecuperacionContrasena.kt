package equipo.ptc.proyecto_trincheraptc

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

class RecuperacionContrasena : AppCompatActivity() {
    companion object {
        var codigoVer: String = ""
        var correo : String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacion_contrasena)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgRecupAtras = findViewById<ImageView>(R.id.imgRecupAtras)
        imgRecupAtras.setOnClickListener {

            val Login = Intent(this, Login::class.java)
            startActivity(Login)
        }

        val txtCorreoR = findViewById<EditText>(R.id.txtRecupCorreoElectronico)
        val btnRecuperar = findViewById<Button>(R.id.btnRecupContinuar)

        btnRecuperar.setOnClickListener {
            correo = txtCorreoR.text.toString().trim()

            if (correo.isNotEmpty()) {
                codigoVer = generarCodigoVerificacion()
                val mensaje = generarMensajeHtml(codigoVer)

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        withContext(Dispatchers.IO) {
                            enviarCorreo(correo, "Recuperación de contraseña", mensaje)
                        }
                        Toast.makeText(this@RecuperacionContrasena, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show()
                        txtCorreoR.text.clear()
                    } catch (e: Exception) {
                        Toast.makeText(this@RecuperacionContrasena, "Error al enviar el correo", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this@RecuperacionContrasena, RecuperacionContrasena2::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this@RecuperacionContrasena, "Ingrese un correo electrónico valido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generarCodigoVerificacion(): String {
        val caracteres = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (1..6).map { caracteres.random() }.joinToString("")
    }

    private fun generarMensajeHtml(codigo: String): String {
        return """
        <!DOCTYPE html>
        <html lang="es">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body {
                    font-family: 'Arial', sans-serif;
                    background-color: #e8f5e9;
                    color: #333;
                    margin: 0;
                    padding: 0;
                }
                .container {
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #fff;
                    border-radius: 10px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    overflow: hidden;
                }
                .header {
                    background-color: #2e7d32;
                    color: #fff;
                    padding: 20px;
                    text-align: center;
                }
                .header h1 {
                    margin: 0;
                }
                .content {
                    padding: 20px;
                    text-align: center;
                }
                .content p {
                    font-size: 16px;
                    margin-bottom: 20px;
                }
                .code {
                    display: inline-block;
                    padding: 10px 20px;
                    background-color: #fbc02d;
                    color: #2e7d32;
                    font-size: 24px;
                    border-radius: 5px;
                    margin-bottom: 20px;
                }
                .footer {
                    background-color: #fbc02d;
                    color: #2e7d32;
                    padding: 10px;
                    text-align: center;
                }
                .footer p {
                    margin: 0;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Recuperación de contraseña</h1>
                </div>
                <div class="content">
                    <p>Para recuperar tu contraseña, ingresa el siguiente código en nuestra aplicación:</p>
                    <div class="code">$codigo</div>
                    <p>Si te has equivocado, sólo ignora este correo.</p>
                </div>
                <div class="footer">
                    <p>No compartas tu contraseña con extraños.</p>
                </div>
            </div>
        </body>
        </html>
    """.trimIndent()
    }
}
