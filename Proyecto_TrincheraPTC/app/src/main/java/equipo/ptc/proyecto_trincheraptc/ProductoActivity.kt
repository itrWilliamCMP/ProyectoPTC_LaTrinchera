package equipo.ptc.proyecto_trincheraptc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_producto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtProducto = findViewById<TextView>(R.id.txtProducto)
        val txtCantidad = findViewById<TextView>(R.id.txtCantidad)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcion)
        val txtPrecio = findViewById<TextView>(R.id.txtPrecio)
        val imgRegresar = findViewById<ImageView>(R.id.imgRegresar)

        val producto = intent.getStringExtra("producto")
        val stock = intent.getStringExtra("stock")
        val descripcion = intent.getStringExtra("descripcion")
        val precioventa = intent.getStringExtra("precioventa")

        txtProducto.text = producto
        txtCantidad.text = stock
        txtDescripcion.text = descripcion
        txtPrecio.text = precioventa

        imgRegresar.setOnClickListener {
            val pantallaDetalleMenus = Intent(this, MenuCategoriaActivity::class.java)
            startActivity(pantallaDetalleMenus)
        }
    }
}