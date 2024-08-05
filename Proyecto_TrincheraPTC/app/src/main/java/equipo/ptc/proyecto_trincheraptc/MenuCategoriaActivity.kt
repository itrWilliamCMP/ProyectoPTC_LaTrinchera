package equipo.ptc.proyecto_trincheraptc


import Modelo.MenuComidas
import RecyclerViewHelpers.AdaptadorMenuCategorias
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MenuCategoriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_categoria)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.rvMenuCategoria)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val comidaNombre = intent.getStringExtra("comidaNombre")

        val comidas: List<MenuComidas> = when (comidaNombre) {
            "Tacos" -> listOf(
                MenuComidas("Tacos al pastor", "Los tacos al pastor son un platillo icónico de " +
                        "la gastronomía mexicana, especialmente popular en la Ciudad de México. " +
                        "Este platillo consiste en tortillas de maíz rellenas de carne de cerdo adobada, que se " +
                        "cocina en un trompo vertical", R.drawable.tacos_al_pastor, 3.50),
                MenuComidas("Tacos de bistec", "Los tacos de bistec son un platillo típico de " +
                        "la cocina mexicana que consiste en tortillas, generalmente de maíz, rellenas de carne de " +
                        "res (bistec) picada o en tiras, cocinada a la parrilla o en una plancha. Este platillo es " +
                        "una de las variantes más populares de los tacos debido a su sabor jugoso y su preparación " +
                        "sencilla.", R.drawable.tacos_de_bistec, 3.50),
                MenuComidas("Tacos de lengua", "Los tacos de lengua son un tipo de taco en la " +
                        "cocina mexicana que utiliza lengua de res cocida como el ingrediente principal. Este tipo de " +
                        "taco es muy apreciado por su textura suave y su sabor único, y es una de las muchas variantes " +
                        "de tacos que se encuentran en la rica tradición culinaria de México.", R.drawable.tacos_de_lengua, 4.50),
                MenuComidas("Tacos de conchita pibil", "Los tacos de cochinita pibil " +
                        "son un platillo tradicional de la cocina mexicana, específicamente de la región " +
                        "de Yucatán. Estos tacos consisten en tortillas de maíz o harina rellenas de cochinita " +
                        "pibil, que es carne de cerdo marinada en achiote y otros condimentos, envuelta en " +
                        "hojas de plátano y cocida lentamente.", R.drawable.tacos_de_bistec, 3.00),
                MenuComidas("Tacos de conchita pibil", "Los tacos de cochinita pibil " +
                        "son un platillo tradicional de la cocina mexicana, específicamente de la región " +
                        "de Yucatán. Estos tacos consisten en tortillas de maíz o harina rellenas de cochinita " +
                        "pibil, que es carne de cerdo marinada en achiote y otros condimentos, envuelta en " +
                        "hojas de plátano y cocida lentamente.", R.drawable.tacos_de_bistec, 3.00)
            )
            "Tortas" -> listOf(
                MenuComidas("Torta de carne", "Una torta de carne de res mexicana es un " +
                        "platillo tradicional de la gastronomía mexicana que consiste en un sándwich hecho " +
                        "con un bolillo o telera (tipos de pan), relleno principalmente de carne de res y " +
                        "acompañado de una variedad de ingredientes y salsas.", R.drawable.torta_de_carne, 3.50),
                MenuComidas("Torta de pollo", "Una torta de pollo mexicana es un tipo de " +
                        "sándwich popular en México que consiste en un pan tipo bolillo o telera, relleno de " +
                        "pollo cocido y desmenuzado, acompañado de una variedad de ingredientes y aderezos que " +
                        "pueden incluir aguacate, jitomate, cebolla, chiles, mayonesa, entre otros. Es una opción " +
                        "rápida y sabrosa para el desayuno, almuerzo o cena.", R.drawable.torta_de_pollo, 3.50),
                MenuComidas("Torta de birria", "La torta de birria es una combinacion de " +
                        "guiso de cordero y pan de la cocina mexicana, una deliciosa y reconfortante opción p" +
                        "ara el desayuno, el almuerzo o la cena. Se trata de un sándwich hecho con pan bolillo o " +
                        "telera, relleno de guiso de carne de cordero, lechuga, cebolla y " +
                        "queso.", R.drawable.torta_de_birria, 4.50),
                MenuComidas("Torta de jamon", "La torta de jamón es un clásico de la cocina " +
                        "mexicana, una deliciosa y reconfortante opción para el desayuno, el almuerzo o la cena. " +
                        "Se trata de un sándwich hecho con pan bolillo o telera, relleno de lonchas de jamón, " +
                        "queso, cebolla, tocino y tomate.", R.drawable.torta_de_jamon, 3.00)
            )
            else -> emptyList()
        }

        val adapter = AdaptadorMenuCategorias(comidas)
        recyclerView.adapter = adapter

        val imgBackSopas = findViewById<ImageView>(R.id.imgBackSopas)
        imgBackSopas.setOnClickListener {
            val MainActivity = Intent(this, MainActivity::class.java)
            startActivity(MainActivity)
        }
    }
}