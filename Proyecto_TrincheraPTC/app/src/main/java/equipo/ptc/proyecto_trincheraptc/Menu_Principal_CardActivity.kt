package equipo.ptc.proyecto_trincheraptc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.nio.file.Path
import java.util.UUID

class Menu_Principal_CardActivity : AppCompatActivity() {

    val codigo_opcion_galeria = 102
    val codigo_opcion_tomar_foto = 103
    val CAMERA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE = 1

    lateinit var ivImagenCategoria: ImageView
    lateinit var miPath: String
    lateinit var tvNombreCategoria: TextView

    val uuid = UUID.randomUUID().toString()

    @SuppressLint("MissingInflatedId", "CutPasteId")
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

        ivImagenCategoria = findViewById(R.id.ivImagenCategoria)
        val tvNombreCategoria = findViewById<TextView>(R.id.tvNombreCategoria)
    }
}