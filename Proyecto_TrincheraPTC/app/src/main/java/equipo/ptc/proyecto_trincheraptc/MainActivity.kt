package equipo.ptc.proyecto_trincheraptc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgMenuOutline = findViewById<ImageView>(R.id.imgMenuOutline)
        imgMenuOutline.setOnClickListener {
            val pantallaLogin = Intent(this, Menu_PrincipalActivity::class.java)
            startActivity(pantallaLogin)
        }

        val imgCarrito = findViewById<ImageView>(R.id.imgCarritoOutline)
        imgCarrito.setOnClickListener {
            val pantallaLogin = Intent(this, CarroCompras::class.java)
            startActivity(pantallaLogin)
        }

        val imgPerfilOutline = findViewById<ImageView>(R.id.imgPerfilOutline)
        imgPerfilOutline.setOnClickListener {
            val pantallaLogin = Intent(this, Perfil::class.java)
            startActivity(pantallaLogin)
        }

        val imgSopas = findViewById<ImageView>(R.id.imgSopas)
        imgSopas.setOnClickListener {
            val pantallaLogin = Intent(this, MenuCategoriaActivity::class.java)
            startActivity(pantallaLogin)
        }

        val imgBaseSopas = findViewById<ImageView>(R.id.imgBaseSopas)
        imgBaseSopas.setOnClickListener {
            val pantallaLogin = Intent(this, MenuCategoriaActivity::class.java)
            startActivity(pantallaLogin)
        }

    }



}