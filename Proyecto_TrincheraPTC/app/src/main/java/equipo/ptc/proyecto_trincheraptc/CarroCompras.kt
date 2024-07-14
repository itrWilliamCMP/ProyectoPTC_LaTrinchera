package equipo.ptc.proyecto_trincheraptc

import Modelo.tbProductos
import RecyclerViewHelpers.AdaptadorCarroCompras
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import equipo.ptc.proyecto_trincheraptc.databinding.ActivityCarroComprasBinding


class CarroCompras : AppCompatActivity() {

    private lateinit var binding: ActivityCarroComprasBinding
    private lateinit var adapter: AdaptadorCarroCompras

    var carroCompras = ArrayList<tbProductos>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carro_compras)
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

        val imgPrincipalOutline = findViewById<ImageView>(R.id.imgPrincipalOutline)
        imgPrincipalOutline.setOnClickListener {
            val pantallaLogin = Intent(this, MainActivity::class.java)
            startActivity(pantallaLogin)
        }

        val imgPerfilOutline = findViewById<ImageView>(R.id.imgPerfilOutline)
        imgPerfilOutline.setOnClickListener {
            val pantallaLogin = Intent(this, Perfil::class.java)
            startActivity(pantallaLogin)
        }
        fun setupRecyclerView() {
            binding.rvListaCarro.layoutManager = LinearLayoutManager(this)
            adapter = AdaptadorCarroCompras(binding.TvTotal, carroCompras)
            binding.rvListaCarro.adapter = adapter
        }

    }
}