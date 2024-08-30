package RecyclerViewHelpers

import Modelo.dataClassComida
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.R

class ViewHolderCategoriaProducto(view: View): RecyclerView.ViewHolder(view) {

    val tvNombreProducto : TextView = view.findViewById(R.id.tvNombreProducto)
    val ivImagenProducto : ImageView = view.findViewById(R.id.ivImagenProducto)

    fun render(Datos: dataClassComida){
        Glide.with(itemView.context).
        load(Datos.Imagen_Producto).
        into(ivImagenProducto)
    }

}