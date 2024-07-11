package equipo.ptc.proyecto_trincheraptc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalleMenu_card : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_card)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgFlechaAdelante = findViewById<ImageView>(R.id.imgFlechaAdelante)

        imgFlechaAdelante.setOnClickListener {
            val pantallaMenu = Intent(this, ProductoActivity::class.java)
            startActivity(pantallaMenu)
        }
        val txtNombreCard = findViewById<TextView>(R.id.txtnombreCard)

        imgFlechaAdelante.setOnClickListener {
            val pantallaMenu = Intent(this, ProductoActivity::class.java)
            startActivity(pantallaMenu)
        }

        val imgAdelante = findViewById<ImageView>(R.id.imgAdelante)

        imgFlechaAdelante.setOnClickListener {
            val pantallaMenu = Intent(this, ProductoActivity::class.java)
            startActivity(pantallaMenu)
        }
    }
}