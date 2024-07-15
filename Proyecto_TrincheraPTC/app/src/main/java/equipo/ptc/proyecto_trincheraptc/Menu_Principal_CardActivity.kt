package equipo.ptc.proyecto_trincheraptc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Menu_Principal_CardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgMenuPrincipal = findViewById<ImageView>(R.id.ivImagenCategoria)
        imgMenuPrincipal.setOnClickListener {
            val pantallaDetalleMenu = Intent(this, MenuCategoriaActivity::class.java)
            startActivity(pantallaDetalleMenu)
        }

        val txtCardMenu = findViewById<TextView>(R.id.tvNombreCategoria)
        txtCardMenu.setOnClickListener {
            val pantallaDetalleMenu = Intent(this, MenuCategoriaActivity::class.java)
            startActivity(pantallaDetalleMenu)
        }
    }
}