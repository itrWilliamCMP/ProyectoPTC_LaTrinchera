package RecyclerViewHelpers

import Modelo.dataClassComida
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        val item = Datos[position]
        holder.tvNombreProducto.text = item.Producto

        holder.render(item)
            Glide.with(holder.itemView)
            .load(item.Imagen_Producto)
            .into(holder.ivImagenProducto)
    }
}