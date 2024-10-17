package RecyclerViewHelpers

import Modelo.tbMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import equipo.ptc.proyecto_trincheraptc.R

class View_Folder_RepartidorMenu(view: View) : RecyclerView.ViewHolder(view) {

    //Mando a llamar elementos de card
    val TxtNombre_Cliente: TextView = view.findViewById(R.id.TxtNombre_Cliente)
    val TxtUbicacion: TextView = view.findViewById(R.id.TxtUbicacion)
    val ivImagenClientes: ImageView = view.findViewById(R.id.ivImagenClientes)
    val flecha: ImageView = view.findViewById(R.id.flechaCategoria)


    fun render(Datos: tbMenu) {
        Glide.with(itemView.context).load(Datos.imagen_categoria).into(ivImagenClientes)
    }
}