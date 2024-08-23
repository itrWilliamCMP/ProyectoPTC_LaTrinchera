package RecyclerViewHelpers

import Modelo.tbMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.R

class ViewHolderCategoriaProducto(view: View): RecyclerView.ViewHolder(view) {

    val tvNombreProducto : TextView = view.findViewById(R.id.tvNombreProducto)
    val ivImagenCategoria : ImageView = view.findViewById(R.id.ivImagenCategoria)

    fun render(Datos: tbMenu){
        Glide.with(itemView.context).
        load(Datos.imagen_categoria).
        into(ivImagenCategoria)
    }

}