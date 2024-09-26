package RecyclerViewHelpers

import Modelo.dataClassComida
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.MenuCategoriaActivity
import equipo.ptc.proyecto_trincheraptc.ProductoActivity
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorComidas(var Datos: List<dataClassComida>) : RecyclerView.Adapter<ViewHolderCategoriaProducto>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategoriaProducto {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_detalle_card, parent, false)
        return ViewHolderCategoriaProducto(view)
    }

    override fun getItemCount() = Datos.size


    override fun onBindViewHolder(holder: ViewHolderCategoriaProducto, position: Int) {
        val dataClassComida = Datos[position]
        holder.tvNombreProducto.text = dataClassComida.producto

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val pantallaDetalle = Intent(context, ProductoActivity::class.java)
            pantallaDetalle.putExtra("producto", dataClassComida.producto)
            pantallaDetalle.putExtra("imagen_comida", dataClassComida.imagen_comida)
            pantallaDetalle.putExtra("id_menu", dataClassComida.id_menu)
            context.startActivity(pantallaDetalle)
        }

    }
}