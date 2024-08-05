package equipo.ptc.proyecto_trincheraptc

import RecyclerViewHelpers.AdaptadorMenuCategorias
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuRepartidor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_repartidor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val PerfilRepartidor1 = findViewById<ImageView>(R.id.PerfilRepartidor1)
        PerfilRepartidor1.setOnClickListener {

            val MenuRepartidor2 = Intent(this, MenuRepartidor2::class.java)
            startActivity(MenuRepartidor2)



        }
    }
}