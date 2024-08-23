package RecyclerViewHelpers

import Modelo.tbMenu
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.R

class AdaptadorCategoriaMenu (private var Datos: List<tbMenu> ): RecyclerView.Adapter<ViewHolderCategoriaProducto>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCategoriaProducto {
        val vistaMenu = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalle_card, parent, false)

        return ViewHolderCategoriaProducto(vistaMenu)
    }
    override fun getItemCount(): Int {
        return Datos.size
    }
    override fun onBindViewHolder(holder: ViewHolderCategoriaProducto, position: Int) {
        val dato = Datos[position]
        holder.tvNombreProducto.text = dato.categoria
        holder.render(dato)
        Glide.with(holder.itemView)
            .load(dato.imagen_categoria)
            .circleCrop()
            .into(holder.ivImagenCategoria)
    }


}