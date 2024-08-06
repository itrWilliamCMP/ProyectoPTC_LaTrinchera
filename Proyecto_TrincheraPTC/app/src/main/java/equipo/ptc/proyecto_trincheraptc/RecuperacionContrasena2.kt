package equipo.ptc.proyecto_trincheraptc

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecuperacionContrasena2 : AppCompatActivity() {
    private lateinit var code1: EditText
    private lateinit var code2: EditText
    private lateinit var code3: EditText
    private lateinit var code4: EditText
    private lateinit var code5: EditText
    private lateinit var code6: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacion_contrasena2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgRecup2Atras = findViewById<ImageView>(R.id.imgRecup2Atras)
        imgRecup2Atras.setOnClickListener {

            val recuperacionContrasena = Intent(this, RecuperacionContrasena::class.java)
            startActivity(recuperacionContrasena)
        }

        code1 = findViewById(R.id.code1)
        code2 = findViewById(R.id.code2)
        code3 = findViewById(R.id.code3)
        code4 = findViewById(R.id.code4)
        code5 = findViewById(R.id.code5)
        code6 = findViewById(R.id.code6)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    when (currentFocus?.id) {
                        R.id.code1 -> code2.requestFocus()
                        R.id.code2 -> code3.requestFocus()
                        R.id.code3 -> code4.requestFocus()
                        R.id.code4 -> code5.requestFocus()
                        R.id.code5 -> code6.requestFocus()
                        R.id.code6 -> {
                            // Aquí verificamos el código
                            val codigoIngresado =
                                "${code1.text}${code2.text}${code3.text}${code4.text}${code5.text}${code6.text}"
                            val codigoCorrecto = RecuperacionContrasena.codigoVer

                            if (codigoIngresado == codigoCorrecto) {
                                // Redirigir a la pantalla de cambio de contraseña
                                val intent = Intent(
                                    this@RecuperacionContrasena2,
                                    RecuperacionContrasena3::class.java
                                )
                                startActivity(intent)
                            } else {
                                // Mostrar mensaje de código incorrecto
                                Toast.makeText(
                                    this@RecuperacionContrasena2,
                                    "Código incorrecto",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        code1.addTextChangedListener(textWatcher)
        code2.addTextChangedListener(textWatcher)
        code3.addTextChangedListener(textWatcher)
        code4.addTextChangedListener(textWatcher)
        code5.addTextChangedListener(textWatcher)
        code6.addTextChangedListener(textWatcher)
    }
}