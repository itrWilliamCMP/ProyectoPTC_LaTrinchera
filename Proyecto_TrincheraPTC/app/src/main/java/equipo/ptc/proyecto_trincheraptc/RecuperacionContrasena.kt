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
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 40px auto;
            background-color: #fff;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            border: 2px solid #d32f2f;
        }
        .header {
            background: linear-gradient(45deg, #d32f2f, #f44336);
            padding: 40px;
            text-align: center;
            color: #fff;
            border-bottom: 5px solid #fbc02d;
            border-radius: 15px 15px 0 0;
        }
        .header h1 {
            margin: 0;
            font-size: 28px;
            font-weight: bold;
            letter-spacing: 1.5px;
        }
        .header p {
            font-size: 16px;
            margin-top: 10px;
            color: #ffebee;
        }
        .content {
            padding: 30px;
            text-align: center;
            background-color: #fafafa;
        }
        .content p {
            font-size: 18px;
            color: #333;
            line-height: 1.6;
        }
        .code {
            display: inline-block;
            padding: 15px 30px;
            background-color: #43a047; /* Verde elegante */
            color: #fff;
            font-size: 36px;
            font-weight: bold;
            border-radius: 10px;
            letter-spacing: 5px;
            margin: 20px 0;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        }
        .cta {
            margin-top: 30px;
            padding: 10px 25px;
            background-color: #d32f2f;
            color: #fff; /* Texto blanco */
            font-size: 16px;
            font-weight: bold; /* Negrita */
            text-transform: uppercase;
            text-decoration: none;
            border-radius: 25px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
            transition: background-color 0.3s ease;
            display: inline-block;
        }
        .cta:hover {
            background-color: #b71c1c;
        }
        .footer {
            background-color: #fbc02d;
            color: #333;
            padding: 20px;
            text-align: center;
            border-radius: 0 0 15px 15px;
            font-size: 14px;
        }
        .footer p {
            margin: 0;
        }
        .footer a {
            color: #fff; /* Color blanco */
            text-decoration: none;
            font-weight: bold;
            transition: color 0.3s ease;
        }
        .footer a:hover {
            color: #ffebee;
        }
        .divider {
            height: 2px;
            background-color: #fbc02d;
            margin: 20px auto;
            width: 80%;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Recuperación de Contraseña</h1>
            <p>¡No te preocupes, estamos aquí para ayudarte!</p>
        </div>
        <div class="content">
            <p>Hemos recibido una solicitud para restablecer tu contraseña. Por favor, introduce el siguiente código de verificación en nuestra aplicación:</p>
            <div class="code">$codigo</div>
            <p>Si no solicitaste esta acción, puedes ignorar este correo, tu cuenta está segura.</p>
            <a href="mailto:latrinchera05@gmail.com" class="cta">Contactar Soporte</a>
        </div>
        <div class="divider"></div>
        <div class="footer">
            <p>No compartas tu código o contraseña con nadie.</p>
            <p>Si tienes alguna duda, <a href="mailto:latrinchera05@gmail.com">contáctanos</a></p>
        </div>
    </div>
</body>
</html>

    """.trimIndent()
    }
}
