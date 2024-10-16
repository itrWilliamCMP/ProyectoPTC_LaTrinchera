package RecyclerViewHelpers

import Modelo.tbMenuConProductos
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorMenu(private var datos: List<tbMenuConProductos>) :
    RecyclerView.Adapter<AdaptadorMenu.ViewHolderMenu>() {

    companion object varGlobaAdap {
        lateinit var id_menu: String
        lateinit var categoria: String
        lateinit var id_producto: String
        lateinit var producto: String
        lateinit var descripcion: String
        lateinit var precioventa: String
        lateinit var stock: String
        lateinit var imagen_categoria: String
        lateinit var imagen_producto: String

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMenu {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_menu_principal_card, parent, false)
        return ViewHolderMenu(vista)
    }

    override fun getItemCount() = datos.size

    override fun onBindViewHolder(holder: ViewHolderMenu, position: Int) {
        val item = datos[position]
        holder.tvNombreCategoria.text = item.categoria

        Glide.with(holder.itemView.context)
            .load(item.imagen_categoria)
            .into(holder.imgComida)

        println("este es la url ${item.imagen_comida}")


        holder.itemView.setOnClickListener { view ->
            val context = view.context
            val intent = Intent(context, MenuCategoriaActivity::class.java)


                /*.apply {

                putExtra("id_producto", item.id_producto)
                putExtra("id_menu", item.id_menu)
                putExtra("categoria", item.categoria)
                putExtra("producto", item.producto)
                putExtra("descripcion", item.descripcion)
                putExtra("precioventa", item.precioventa)
                putExtra("stock", item.stock)
                putExtra("imagen_categoria", item.imagen_categoria)
                putExtra("imagen_producto", item.imagen_comida)
            }*/
            id_producto = item.id_producto.toString()
            id_menu = item.id_menu.toString()
            categoria = item.categoria
            producto = item.producto
            descripcion = item.descripcion
            precioventa = item.precioventa.toString()
            stock = item.stock.toString()
            imagen_categoria = item.imagen_categoria
            imagen_producto = item.imagen_comida

            context.startActivity(intent)
        }
    }

    class ViewHolderMenu(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreCategoria: TextView = itemView.findViewById(R.id.tvNombreCategoria)
        val imgComida : ImageView = itemView.findViewById(R.id.ivImagenProductoCard)
    }
}