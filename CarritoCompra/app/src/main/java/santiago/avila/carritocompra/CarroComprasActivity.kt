package santiago.avila.carritocompra

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import santiago.avila.carritocompra.databinding.ActivityCarroComprasBinding


class CarroComprasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarroComprasBinding
    private lateinit var adapter: AdaptadorCarroCompras

    var carroCompras = ArrayList<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarroComprasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carroCompras = intent.getSerializableExtra("carro_compras") as ArrayList<Producto>

        setupRecyclerView()
    }

    fun setupRecyclerView() {
        binding.rvListaCarro.layoutManager = LinearLayoutManager(this)
        adapter = AdaptadorCarroCompras(binding.TvTotal, carroCompras)
        binding.rvListaCarro.adapter = adapter
    }
}